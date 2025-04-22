import { Component,inject } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-error',
  imports: [],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css'
})
export class ErrorComponent {
  // Default error message (used if no query param is provided)
  errorMessage: string = 'An unexpected error occurred.';
  // Inject Angular Router
  private router = inject(Router);

  constructor(private route: ActivatedRoute) {
    // Listen for query parameters to display a custom error message
    this.route.queryParams.subscribe(params => {
      // Set error message from the query string, or fall back to default
      this.errorMessage = params['message'] || this.errorMessage;
    });
  }
  // Navigate back to the main vehicle list page
  goBack() {
    this.router.navigate(['/vehicles']);
  }
}