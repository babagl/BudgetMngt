import axios from 'axios';
import React, { useEffect, useState } from 'react';


interface Transaction {
  id: string; 
  description: string;
  amount: number | null; 
  date: string; 
  transactionType: 'DEPENSE' | 'REVENU'; 
}

const BudgetApp = () => {
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [isEditing, setIsEditing] = useState<string | null>(null); 
  const [formData, setFormData] = useState<Omit<Transaction, 'id' | 'date'>>({
    description: '',
    amount: 0, 
    transactionType: 'DEPENSE',
  });

  useEffect(() => {
    axios
      .get<Transaction[]>('http://localhost:8888/api/transactions')
      .then((res) => {
        setTransactions(res.data);
      })
      .catch((error) => {
        console.error('Erreur lors de la récupération des transactions :', error);
      });
  }, []);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    
    if (!formData.description || (formData.amount ?? 0) <= 0) {
      alert('Veuillez remplir tous les champs obligatoires avec des valeurs valides.');
      return;
    }

    if (isEditing) {
      
      axios
        .put<Transaction>(`http://localhost:8888/api/transactions/${isEditing}`, formData)
        .then((res) => {
          setTransactions((prev) =>
            prev.map((t) => (t.id === isEditing ? res.data : t))
          );
          setIsEditing(null);
          setFormData({ description: '', amount: 0, transactionType: 'DEPENSE' });
        })
        .catch((error) => {
          console.error('Erreur lors de la mise à jour de la transaction :', error);
        });
    } else {
      
      axios
        .post<Transaction>('http://localhost:8888/api/transactions', formData)
        .then((res) => {
          setTransactions((prev) => [...prev, res.data]);
          setFormData({ description: '', amount: 0, transactionType: 'DEPENSE' });
        })
        .catch((error) => {
          console.error('Erreur lors de l’ajout de la transaction :', error);
        });
    }
  };

  const startEdit = (transaction: Transaction) => {
    setIsEditing(transaction.id);
    setFormData({
      description: transaction.description,
      amount: transaction.amount ?? 0, 
      transactionType: transaction.transactionType,
    });
  };

  const deleteTransaction = (id: string) => {
    axios
      .delete(`http://localhost:8888/api/transactions/${id}`)
      .then(() => {
        setTransactions((prev) => prev.filter((t) => t.id !== id));
      })
      .catch((error) => {
        console.error('Erreur lors de la suppression de la transaction :', error);
      });
  };

  const total = transactions.reduce(
    (acc, t) => acc + (t.transactionType === 'REVENU' ? (t.amount ?? 0) : -(t.amount ?? 0)),
    0
  );

  return (
    <div className="max-w-4xl mx-auto p-4 space-y-6">
      
      <div className="bg-white shadow-md rounded-lg p-6">
        <h2 className="text-2xl font-semibold mb-4">Gestion de Budget</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="flex flex-col">
            <label htmlFor="description" className="text-sm font-medium text-gray-700">
              Description
            </label>
            <input
              id="description"
              type="text"
              placeholder="Description"
              value={formData.description}
              onChange={(e) =>
                setFormData({ ...formData, description: e.target.value })
              }
              className="mt-1 p-2 border rounded shadow-sm focus:ring focus:ring-blue-300 w-full"
              required
            />
          </div>
          
          <div className="flex flex-col">
            <label htmlFor="amount" className="text-sm font-medium text-gray-700">
              Montant
            </label>
            <input
              id="amount"
              type="number"
              step="0.01"
              min="0"
              placeholder="Montant"
              value={formData.amount ?? ''}
              onChange={(e) =>
                setFormData({ ...formData, amount: parseFloat(e.target.value) || 0 })
              }
              className="mt-1 p-2 border rounded shadow-sm focus:ring focus:ring-blue-300 w-full"
              required
            />
          </div>
          
          <div className="flex flex-col">
            <label htmlFor="transactionType" className="text-sm font-medium text-gray-700">
              Type de transaction
            </label>
            <select
              id="transactionType"
              value={formData.transactionType}
              onChange={(e) =>
                setFormData({ ...formData, transactionType: e.target.value as 'DEPENSE' | 'REVENU' })
              }
              className="mt-1 p-2 border rounded shadow-sm focus:ring focus:ring-blue-300 w-full"
            >
              <option value="DEPENSE">Dépense</option>
              <option value="REVENU">Revenu</option>
            </select>
          </div>

          <button
            type="submit"
            className="mt-4 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 focus:ring focus:ring-blue-300 focus:outline-none w-full font-semibold"
          >
            {isEditing ? 'Modifier' : 'Ajouter'}
          </button>
        </form>
      </div>

      
      <div className="bg-white shadow-md rounded-lg p-6">
        <h2 className="text-2xl font-semibold mb-4">Transactions</h2>
        <div className="text-xl mb-4">
          Solde :{' '}
          <span className={total >= 0 ? 'text-green-600' : 'text-red-600'}>
            {total.toFixed(2)}FRANCS CFA
          </span>
        </div>
        <div className="space-y-2">
          {transactions.map((t) => (
            <div
              key={t.id}
              className="flex items-center justify-between p-2 border rounded"
            >
              <div className="flex-grow">
                <div className="font-medium">{t.description}</div>
                <div className="text-sm text-gray-500">
                  {t.transactionType} • {t.date} •{' '}
                  {(t.amount ?? 0).toFixed(2)}€
                </div>
              </div>
              <div className="flex gap-2">
                <button
                  className="text-blue-500 hover:text-blue-700"
                  onClick={() => startEdit(t)}
                >
                  Modifier
                </button>
                <button
                  className="text-red-500 hover:text-red-700"
                  onClick={() => deleteTransaction(t.id)}
                >
                  Supprimer
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default BudgetApp;
