(function ($) {
    "use strict";
    var mainApp = {

        initFunction: function () {
            /*MENU
            ------------------------------------*/
            $('#main-menu').metisMenu();

            $(window).bind("load resize", function () {
                if ($(this).width() < 768) {
                    $('div.sidebar-collapse').addClass('collapse')
                } else {
                    $('div.sidebar-collapse').removeClass('collapse')
                }
            });
            /* MORRIS DONUT CHART
			----------------------------------------*/
            Morris.Donut({
                element: 'Taskrl',
                data: Taskrl,
                colors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                resize: true
            });

            Morris.Donut({
                element: 'Picnum',
                data: Picnum,
                colors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                resize: true
            });

            Morris.Donut({
                element: 'Payment',
                data: Payment,
                colors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                resize: true
            });

            /* MORRIS LINE CHART
			----------------------------------------*/
            Morris.Line({
                element: 'Payment-line',
                data: Payment_line,


                xkey: 'y',
                ykeys: ['a', 'b','c'],
                labels: ['整体标注', '方框框选','方框标注'],
                fillOpacity: 0.6,
                hideHover: 'auto',
                behaveLikeLine: true,
                resize: true,
                pointFillColors:['#ffffff'],
                pointStrokeColors: ['black'],
                lineColors:['gray','#1cc09f','#1dd08c']

            });

            Morris.Line({
                element: 'Speed-line',
                data: Speed_line,


                xkey: 'y',
                ykeys: ['a', 'b','c'],
                labels: ['整体标注', '方框框选','方框标注'],
                fillOpacity: 0.6,
                hideHover: 'auto',
                behaveLikeLine: true,
                resize: true,
                pointFillColors:['#ffffff'],
                pointStrokeColors: ['black'],
                lineColors:['gray','#1cc09f','#1dd08c']

            });
            /* MORRIS BAR CHART
			-----------------------------------------*/


            Morris.Bar({
                element: 'morris-bar-chart2',
                data:bardata2,
                xkey: 'y',
                ykeys: ['a', 'b','c'],
                labels: ['整体标注', '方框框选','方框标注'],
                barColors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                hideHover: 'auto',
                resize: true
            });

            Morris.Bar({
                element: 'morris-bar-chart3',
                data:bardata,
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['每日注册人数', '每日登录人数'],
                barColors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                hideHover: 'auto',
                resize: true
            });





            /* MORRIS DONUT CHART
			----------------------------------------*/
            Morris.Donut({
                element: 'TaskStatus',
                data: taskstatusdata,
                colors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                resize: true
            });

            /* MORRIS DONUT CHART
			----------------------------------------*/
            Morris.Donut({
                element: 'TaskType',
                data: tasktypedata,
                colors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                resize: true
            });

            /* MORRIS DONUT CHART
           ----------------------------------------*/
            Morris.Donut({
                element: 'YYQ',
                data: tasktypedata,
                colors: [
                    '#A6A6A6','#1cc09f',
                    '#A8E9DC'
                ],
                resize: true
            });



            /* MORRIS AREA CHART
			----------------------------------------*/

            Morris.Area({
                element: 'morris-area-chart',
                data: areadata,
                xkey: 'period',
                ykeys: ['iphone', 'ipad', 'itouch'],
                labels: ['iPhone', 'iPad', 'iPod Touch'],
                pointSize: 2,
                hideHover: 'auto',
                pointFillColors:['#ffffff'],
                pointStrokeColors: ['black'],
                lineColors:['#A6A6A6','#1cc09f'],
                resize: true
            });






        },

        initialization: function () {
            mainApp.initFunction();

        }

    }
    // Initializing ///

    $(document).ready(function () {
        mainApp.initFunction();
        $("#sideNav").click(function(){
            if($(this).hasClass('closed')){
                $('.navbar-side').animate({left: '0px'});
                $(this).removeClass('closed');
                $('#page-wrapper').animate({'margin-left' : '260px'});

            }
            else{
                $(this).addClass('closed');
                $('.navbar-side').animate({left: '-260px'});
                $('#page-wrapper').animate({'margin-left' : '0px'});
            }
        });
    });

}(jQuery));