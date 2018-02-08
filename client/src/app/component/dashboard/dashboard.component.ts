import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public chartType = 'line';

  public chartDatasets: Array<any> = [
    { data: [1, 2, 4, 6, 6], label: '会議予定時間' },
    { data: [2, 3, 6, 8, 10], label: '実績時間' }
  ];

  public chartLabels: Array<any> = ['月', '火', '水', '木', '金'];

  public chartColors: Array<any> = [
    {
      backgroundColor: 'rgba(220,22,220,0.2)',
      borderColor: 'rgba(220,22,220,1)',
      borderWidth: 2,
      pointBackgroundColor: 'rgba(220,220,220,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(220,220,220,1)'
    },
    {
      backgroundColor: 'rgba(151,187,205,0.2)',
      borderColor: 'rgba(151,187,205,1)',
      borderWidth: 2,
      pointBackgroundColor: 'rgba(151,187,205,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(151,187,205,1)'
    }
  ];

  public chartOptions: any = {
    responsive: true
  };

  public chartClicked(e: any): void {

  }

  public chartHovered(e: any): void {

  }
  constructor() { }

  ngOnInit() { }

}
