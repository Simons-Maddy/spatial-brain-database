
function LinkStyle() {
    var urlstr = location.href;
    if (document.getElementsByTagName('a')) {
        var arrLink = document.getElementsByTagName('a');
        for (i = 0; i < arrLink.length; i++) {
            var link = arrLink[i];
            var href = link.getAttribute("href");
            if (href.indexOf('#') > -1 && urlstr.indexOf(href) > -1) {
                link.style.backgroundColor = "Silver";
            } else {
                link.style.backgroundColor = 'WhiteSmoke';
            }
        }
    }
}