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
  public displayedColumns = ['id', 'name', 'room', 'date', 'start', 'end', 'member', 'edit'];
  public dataSource: MatTableDataSource<Member>;
  public ctx: CanvasRenderingContext2D;
  public ELEMENT_DATA: Member[] = [];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<any>;
  @ViewChild('mtgDateCanvas') mtgDateCanvas;
  @ViewChild('mtgCircleGraph') mtgCircleGraph;
  @ViewChild('mtgOvertimeCanvas') mtgOvertimeCanvas;

  constructor() {
    this.memberType = Constants.memberType;
    this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    const ctx = this.mtgDateCanvas.nativeElement;
    this.drawDate(ctx);
    const ctx2 = this.mtgCircleGraph.nativeElement;
    this.drawCircle(ctx2);
    const ctx3 = this.mtgOvertimeCanvas.nativeElement;
    this.drawOvertime(ctx3);
  }

  drawDate(ctx) {
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

  drawOvertime(ctx) {
    return new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['3/1', '3/2', '3/5', '3/6', '3/7', '3/8', '3/9', '3/12', '3/13', '3/14', '3/15',
          '3/16', '3/19', '3/20', '3/22', '3/23', '3/26', '3/27', '3/28', '3/29', '3/30', '3/31'],
        datasets: [{
          label: '残業時間',
          data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
          backgroundColor: 'rgba(101,107,255,0.2)',
          borderColor: 'rgba(101,107,255,1)',
          borderWidth: 2,
          pointBackgroundColor: 'rgba(101,107,255,1)',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: 'rgba(101,107,255,1)'
        }, {
          label: 'AI予測',
          data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22],
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


  drawCircle(ctx) {
    return new Chart(ctx, {
      type: 'pie',
      data: {
        datasets: [{
          data: [
            10,
            20,
            30,
            20,
          ],
          backgroundColor: [
            'red',
            'orange',
            'yellow',
            'green',
          ],
          label: 'Dataset 1'
        }],
        labels: [
          '会議超過あり：残業あり',
          '会議超過あり：残業なし',
          '会議超過なし：残業あり',
          '会議超過なし：残業なし'
        ]
      },
      options: {
        responsive: true
      }
    });

  }


}
