$(function () {

    var barChart = echarts.init(document.getElementById("echarts-bar-chart"));
    var baroption = {
        title : {
            text: '订单数量'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['预定量','支付数']
        },
        grid:{
            x:30,
            x2:40,
            y2:24
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'预定量',
                type:'bar',
                data:[10, 12, 29, 2, 3, 34, 15, 44, 7,34, 36, 13],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'支付数',
                type:'bar',
                data:[7, 5, 13, 2, 3, 22, 7, 2, 4,3, 6, 3],
                markPoint : {
                    data : [
                        {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                        {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }
        ]
    };
    barChart.setOption(baroption);

    window.onresize = barChart.resize;



});
