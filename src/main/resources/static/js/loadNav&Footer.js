function loadNavbar() {
    let navbar = document.getElementById("navbar")
    navbar.outerHTML =
        "<nav class=\"navbar navbar-inverse navbar-fixed-top\">\n" +
        "  <div class=\"container\">\n" +
        "    <ul class=\"nav navbar-nav\">\n" +
        "      <li id=\"home\" role=\"presentation\"><a href=\"/home\">Home</a></li>\n" +
        "      <li id=\"annotation\" role=\"presentation\"><a href=\"/annotation\">Annotation</a></li>\n" +
        "      <li id=\"vitessce\" role=\"presentation\"><a href=\"/vitessce\">Vitessce</a></li>\n" +
        "      <li id=\"slideseq\" role=\"presentation\"><a href=\"/slideseq\">Slideseq</a></li>\n" +
        "      <li id=\"help\" role=\"presentation\"><a href=\"/help\">Help</a></li>\n" +
        "      <li id=\"contact\" role=\"presentation\"><a href=\"/contact\">Contact</a></li>\n" +
        "    </ul>\n" +
        "  </div>\n" +
        "</nav>"
}

function loadFooter() {
    let footer = document.getElementById("footer")
    footer.outerHTML =
        "<link rel=\"stylesheet\" href=\"/static/css/footer.css\">\n" +
        "<div id=\"footer\" class=\"modal-footer\">\n" +
        "    <footer class=\"h5\">\n" +
        "        <p>Copyright &copy; 2011-2022 |\n" +
        "            <a href=\"https://www.imm.pku.edu.cn/ywwz/home/index.htm\" target=\"_blank\">Institute of Molecular Medicine (IMM)</a>,\n" +
        "            <a href=\"http://english.pku.edu.cn\" target=\"_blank\">Peking University</a> | All Rights Reserved. |\n" +
        "            E-mail: <a href=\"mailto:xiaochunfu@pku.edu.cn\">xiaochunfu@stu.pku.edu.cn</a>\n" +
        "        </p>\n" +
        "        <p><a href=\"https://beian.miit.gov.cn/integrated/recordquery#/Integrated/recordQuery\" target=\"_blank\" id=\"beian\">\n" +
        "              苏ICP备2021011214号-1</a></p></br>\n" +
        "    </footer>\n" +
        "</div>"
}

function navAction(){
    let url = document.location;
    let navUl = document.querySelector("nav ul");
    let navUlChildren = navUl.children;
    for (let i =0; i < navUlChildren.length; i++)
    {
        if(String(url).match(navUlChildren[i].id)){
            navUlChildren[i].className = "active";
        }
        else
            delete navUlChildren[i].className;
    }
    if(String(url).match("upload") || String(url).match("result"))
        navUlChildren[1].className = "active";
        }

$(function(){
    $(document).ready(function(){
        loadNavbar();
        loadFooter();
        navAction();
    });
})