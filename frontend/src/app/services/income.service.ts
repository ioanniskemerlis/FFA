import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class IncomeService {
  private apiUrl = 'http://localhost:8080/api/incomes';

  constructor(private http: HttpClient) {}

  // Get JWT token from localStorage
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No token found'); // Handle missing token scenario
    }
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  // Get all incomes
  getIncomes(): Observable<any> {
    return this.http.get(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  // Add a new income
  addIncome(income: any): Observable<any> {
    return this.http.post(this.apiUrl, income, { headers: this.getAuthHeaders() });
  }

  // Update an existing income
  updateIncome(id: string, income: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, income, { headers: this.getAuthHeaders() });
  }

  // Delete an income
  deleteIncome(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }
}