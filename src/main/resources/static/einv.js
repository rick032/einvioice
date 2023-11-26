$(function () {
    var date = new Date(), y = date.getFullYear(), m = date.getMonth();
    var thisMonth = new Date(y, m, 1);
    var lastMonth = new Date(y, m - 1, 1);
    var form = $('#searchForm');
    var searchText = $("input[type=search]");
    $(".date").datepicker({
        dateFormat: "yy-mm-dd"
    });
    var m = thisMonth.getMonth()+1;
    var d = thisMonth.getDate();
    var lm = lastMonth.getMonth()+1;
    var ld = lastMonth.getDate()
    $("#startDate").val([lastMonth.getFullYear(),lm>9?lm:'0'+lm,ld>9?ld:'0'+ld].join("-"));
    $("#endDate").val([thisMonth.getFullYear(),m>9?m:'0'+m, d>9?d:'0'+d].join("-"));
    $("tr.accordion-header").hover(function() {
        // $(this).next().slideToggle();
        
        $(this).next("tr.detail").slideDown();
        $(this).siblings().next("tr.detail").slideUp();
        
    });
    $("th.search").click(function (e) {
        $(this).toggleClass('text-warning');
        searchText.attr('placeholder', $(this).hasClass('text-warning') ? '搜尋賣方營業人' : '搜尋明細');
    });

    form.submit(function () {
        var searchVal = searchText.val();
        if (searchVal) {
            var name = $(".text-warning").length?'sellerName':'desc';
            var dom = form.find('#' + name);
            if(dom.length){
                dom.val(searchVal);
            }else {
                $('<input />').attr('type', 'hidden')
                    .attr('name', name)
                    .attr('value', searchVal)
                    .appendTo('#searchForm');
            }
            return true;
        }
        form.find("[type=hidden]").remove();

    });
});