import { Component, OnInit } from '@angular/core';
import { IncomeService } from '../../services/income.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';

@Component({
    selector: 'app-incomes',
    standalone: true,
    imports: [
        CommonModule,
        MatToolbarModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule, // Import MatInputModule
        MatButtonModule,
        FormsModule,
        MatOptionModule,
        MatSelectModule
    ],
    templateUrl: './incomes.component.html',
    styleUrls: ['./incomes.component.scss']
})
export class IncomesComponent implements OnInit {
  incomes: any[] = [];
  newIncome = { type: '', amount: 0, date: '', notes: '' };
  username: string = ''; // Define the username property
  incomeTypes: string[];

  constructor(private incomeService: IncomeService,
    private router: Router,
    
  ) {this.incomeTypes = ['Daily Wage', 'Tips', 'Monthly Salary'];}


  
  ngOnInit(): void {
    this.loadIncomes();
    this.getUsernameFromToken();
  }

  getUsernameFromToken() {
      const token = localStorage.getItem('token'); // Retrieve token from localStorage
      if (token) {
        const decodedToken: any = jwtDecode(token); // Decode the token
        this.username = decodedToken?.sub || 'User'; // Extract the username (usually in `sub` claim)
      } else {
        this.router.navigate(['/auth']); // Redirect to login if no token is found
      }
    }

  loadIncomes() {
    this.incomeService.getIncomes().subscribe({
      next: (data) => (this.incomes = data),
      error: (error) => console.error('Error loading incomes:', error),
    });
  }

  addIncome() {
    this.incomeService.addIncome(this.newIncome).subscribe({
      next: () => {
        alert('Income added successfully!');
        this.newIncome = { type: '', amount: 0, date: '', notes: '' };
        this.loadIncomes();
      },
      error: (error) => console.error('Error adding income:', error),
    });
  }

  deleteIncome(id: string) {
    this.incomeService.deleteIncome(id).subscribe({
      next: () => {
        alert('Income deleted successfully!');
        this.loadIncomes();
      },
      error: (error) => console.error('Error deleting income:', error),
    });
  }
  logout() {
    localStorage.removeItem('token'); // Clear the token
    this.router.navigate(['/auth']); // Redirect to login page
  }

  dashboard(){
    this.router.navigate(['/dashboard']);
  }
}
