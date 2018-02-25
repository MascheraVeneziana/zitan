import { Component, Inject, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTableDataSource, MatSort, MatTable } from '@angular/material';
import { Constants } from '../../class/constants';
import { Member } from '../../class/member';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, AfterViewInit {

  public memberType: Object;
  public displayedColumns = ['id', 'name', 'room', 'date', 'start', 'end', 'member'];
  public dataSource: MatTableDataSource<Member>;
  public ctx: CanvasRenderingContext2D;
  public ELEMENT_DATA: Member[] = [];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<any>;
  @ViewChild('myCanvas') myCanvas;

  constructor() {
    this.memberType = Constants.memberType;
    this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    const ctx = this.myCanvas.nativeElement;
    this.drawChart(ctx);
  }

  drawChart(ctx) {
    return new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['月', '火', '水', '木', '金'],
        datasets: [{
          label: '会議予定時間',
          data: [1, 2, 4, 5, 6],
          backgroundColor: 'rgba(220,22,220,0.2)',
          borderColor: 'rgba(220,22,220,1)',
          borderWidth: 2,
          pointBackgroundColor: 'rgba(220,220,220,1)',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: 'rgba(220,220,220,1)'
        }, {
          label: '実績時間',
          data: [2, 3, 6],
          backgroundColor: 'rgba(101,107,255,0.2)',
          borderColor: 'rgba(101,107,255,1)',
          borderWidth: 2,
          pointBackgroundColor: 'rgba(101,107,255,1)',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: 'rgba(101,107,255,1)'
        }, {
          label: 'AI予測',
          data: [2, 3, 6, 8, 10],
          backgroundColor: 'rgba(200,150,22,0.2)',
          borderColor: 'rgba(200,150,22,1)',
          borderWidth: 2,
          pointBackgroundColor: 'rgba(200,150,22,1)',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: 'rgba(200,150,22,1)'
        }]
      },
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true
            }
          }]
        }
      }
    });

  }

}
