export class ChartConfig {
  public static readonly chartOptions: Object = {
    legend: {
      display: false,
    },
    scales: {
      xAxes: [
        {
          display: true,
          ticks: {
            beginAtZero: true,
          },
        },
      ],
      yAxes: [
        {
          display: true,
          ticks: {
            beginAtZero: true,
            fixedStepSize: 1,
            userCallback: label => {
              return Math.floor(label);
            },
          },
        },
      ],
    },
  };
}
