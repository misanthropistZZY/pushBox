<%--
  Created by IntelliJ IDEA.
  User: zzy
  Date: 2017/11/28
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>

    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #363636;
            background-size: 100%;
            background-position: left;
            background-repeat: repeat-y;

        }

        #yxbox {
            padding: 0;
            margin: 0 auto;
            margin-top: 50px;
            list-style: none;
            position: relative;
        }

        #yxbox li {
            width: 50px;
            height: 50px;
            float: left;
        }

        #yxbox .pos0 {
            background: url('images/bg_tree.png') no-repeat;
        }

        #yxbox .pos1 {
            background: url('images/wall.jpg') no-repeat;
        }

        #yxbox .pos2 {
            background: url('images/allow_bg.jpg') no-repeat;
        }

        #yxbox .pos3 {
            background: url('images/target.jpg') no-repeat;
        }

        #yxbox .person1, #yxbox .person2, #yxbox .person3, #yxbox .person4 {
            width: 50px;
            height: 50px;
            position: absolute;
            background-image: url('images/person.png');
            background-repeat: no-repeat;
        }

        #yxbox .box {
            width: 50px;
            height: 50px;
            position: absolute;
            background-image: url('images/box.jpg');
            background-repeat: no-repeat;
        }

        #yxbox .person1 {
            background-position: 0;
        }

        #yxbox .person2 {
            background-position: -50px 0;
        }

        #yxbox .person3 {
            background-position: -100px 0;
        }

        #yxbox .person4 {
            background-position: -150px 0;
        }

    </style>
</head>
<body>
<ul id="yxbox">

</ul>


<div style="position: fixed;right: 50%;top: 50px;height: 400px;width: 200px;margin-right: -420px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <button id="autoButton" type="button" class="btn btn-success" style="width: 100%;">
                自动求解
            </button>
        </div>
        <div class="panel-body" style="overflow: auto;">
            <p id="resultShow"></p>
        </div>
    </div>
</div>

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.js"></script>
<script>
    var BoxYouXi = {
        oP: null,
        history: [],
        target: {},
        boxs: [],
        temp_boxs: [],
        gk: [
            {
//                map: [
//                    0, 0, 1, 1, 1, 0, 0, 0,
//                    0, 1, 3, 3, 1, 1, 1, 1,
//                    0, 1, 2, 2, 3, 2, 2, 1,
//                    1, 2, 2, 2, 1, 2, 1, 0,
//                    1, 2, 2, 2, 2, 3, 1, 0,
//                    1, 2, 2, 2, 2, 2, 2, 1,
//                    0, 1, 2, 2, 2, 2, 1, 0,
//                    0, 0, 1, 1, 1, 1, 1, 0
//                ],
                map: [
                    0, 0, 0, 1, 1, 0, 0, 0,
                    0, 1, 1, 2, 1, 1, 1, 1,
                    0, 1, 2, 2, 3, 2, 2, 1,
                    1, 2, 2, 2, 1, 2, 1, 0,
                    1, 2, 2, 2, 2, 3, 1, 0,
                    1, 2, 2, 2, 2, 2, 2, 1,
                    0, 1, 2, 2, 2, 2, 1, 0,
                    0, 0, 1, 1, 1, 1, 1, 0
                ],
                boxPos: [
                    //{x: 5, y: 3},
                    //{x: 3, y: 5},
                    {x: 2, y: 4},
                    {x: 4, y: 5}
                ],
                personPos: {x: 4, y: 6},
            },
//            {
//                map: [
//                    0, 0, 1, 1, 1, 0, 0, 0,
//                    0, 1, 3, 3, 1, 1, 1, 1,
//                    0, 1, 2, 2, 2, 2, 2, 1,
//                    1, 2, 2, 2, 1, 2, 2, 1,
//                    1, 2, 2, 1, 2, 3, 2, 1,
//                    1, 2, 2, 2, 2, 2, 2, 1,
//                    1, 3, 2, 2, 2, 2, 1, 0,
//                    1, 3, 2, 2, 2, 2, 1, 0,
//                    0, 1, 1, 1, 1, 1, 1, 0
//                ],
//                boxPos: [
//                    {x: 5, y: 3},
//                    {x: 3, y: 5},
//                    {x: 2, y: 4},
//                    {x: 4, y: 5}
//                ],
//                personPos: {x: 4, y: 6},
//            }
        ],
        createMap:

            function (gk) {
                document.title = '当前第' + (this.num + 1) + '关';
                this.colsNum = Math.sqrt(gk.map.length);
                this.oParent.style.width = this.colsNum * 50 + 'px';
                for (var i = 0; i < gk.map.length; i++) {
                    var oLi = document.createElement('li');
                    oLi.className = 'pos' + gk.map[i];
                    this.oParent.appendChild(oLi);
                    if (gk.map[i] == 3) {
                        this.target[oLi.offsetLeft + '_' + oLi.offsetTop] = '1';
                    }
                }
                this.createMan(gk);
            }

        ,
        createMan: function (gk) {
            this.oP = document.createElement('div');
            this.oP.className = 'person2';
            this.oP.x = gk.personPos.x;
            this.oP.y = gk.personPos.y;
            this.oP.style.left = this.oP.x * 50 + 'px';
            this.oP.style.top = this.oP.y * 50 + 'px';
            this.oParent.appendChild(this.oP);
            this.createBox(gk);
        }
        ,
        createBox: function (gk) {
            for (var i = 0; i < gk.boxPos.length; i++) {
                var oDiv = document.createElement('div');
                oDiv.className = 'box';
                oDiv.style.left = gk.boxPos[i].x * 50 + 'px';
                oDiv.style.top = gk.boxPos[i].y * 50 + 'px';
                this.oParent.appendChild(oDiv);
                this.boxs.push(oDiv);
            }
        }
        ,
        personRun: function (iJons) {
            var that = this

            var gk = this.gk[this.num];
            var map = gk.map;
            var x = this.oP.x + iJons.x;
            var y = this.oP.y + iJons.y;
            var bx = x + iJons.x;
            var by = y + iJons.y;
            if (map[this.colsNum * y + x] == 1) {
                return;
            }
            this.oP.x = x;
            this.oP.y = y;
            this.oP.style.left = this.oP.x * 50 + 'px';
            this.oP.style.top = this.oP.y * 50 + 'px';

            this.peopleP =
                console.log("x:" + x + "  y:" + y)

            // console.log(BoxYouXi)
            console.log(that.boxs)
            console.log(that.oP.x + "     " + that.oP.y)
            that.temp_boxs = [];
            for (var i = 0; i < that.boxs.length; i++) {
                console.log("箱子" + i + " y:" + that.boxs[i].offsetLeft / 50 + "   x:" + that.boxs[i].offsetTop / 50)
                that.temp_boxs.push(that.boxs[i].offsetTop / 50);
                that.temp_boxs.push(that.boxs[i].offsetLeft / 50);
            }

            for (var i = 0; i < this.boxs.length; i++) {

                if (this.impactCheck(this.oP, this.boxs[i])) {
                    if (map[this.colsNum * by + bx] == 1) {
                        this.oP.x = x - iJons.x;
                        this.oP.y = y - iJons.y;
                        this.oP.style.left = this.oP.x * 50 + 'px';
                        this.oP.style.top = this.oP.y * 50 + 'px';
                        return;
                    }
                    this.boxs[i].style.left = bx * 50 + 'px';
                    this.boxs[i].style.top = by * 50 + 'px';
                    for (var n = 0; n < this.boxs.length; n++) {
                        if (this.boxs[i] != this.boxs[n] && this.impactCheck(this.boxs[i], this.boxs[n])) {
                            this.oP.x = x - iJons.x;
                            this.oP.y = y - iJons.y;
                            this.oP.style.left = this.oP.x * 50 + 'px';
                            this.oP.style.top = this.oP.y * 50 + 'px';
                            this.boxs[i].style.left = (bx - iJons.x) * 50 + 'px';
                            this.boxs[i].style.top = (by - iJons.y) * 50 + 'px';
                            return;
                        }
                    }
                    if (this.target[this.boxs[i].offsetLeft + '_' + this.boxs[i].offsetTop]) {
                        this.boxs[i].ok = true;
                        var sucLen = 0;
                        for (var n = 0; n < this.boxs.length; n++) {
                            if (this.boxs[n].ok) {
                                sucLen++;
                            }
                        }
                        if (sucLen == this.boxs.length) this.nextLevel();
                    } else {
                        this.boxs[i].ok = false;
                    }
                    break;
                }
            }
            if (this.preTmp) this.history.push(this.preTmp);
            this.preTmp = {
                boxPos: []
            };
            this.preTmp.personPos = {
                x: this.oP.x,
                y: this.oP.y
            }
            for (var i = 0; i < this.boxs.length; i++) {
                this.preTmp.boxPos[i] = {
                    x: this.boxs[i].offsetLeft,
                    y: this.boxs[i].offsetTop
                }
            }
            if (this.history.length > 20) {
                this.history.splice(0, 1);
            }
        }
        ,
        nextLevel: function () {
            this.history = [];
            this.target = [];
            this.boxs = [];
            this.temp_boxs = [];
            this.oParent.innerHTML = '';
            this.num++;
            if (!this.gk[this.num]) {
                alert('完成！');
                return false;
            }
            this.createMap(this.gk[this.num]);
        }
        ,
        impactCheck: function (obj1, obj2) {//碰撞检测
            var l1 = obj1.offsetLeft;
            var t1 = obj1.offsetTop;
            var r1 = l1 + obj1.offsetWidth;
            var b1 = t1 + obj1.offsetHeight;
            var l2 = obj2.offsetLeft;
            var t2 = obj2.offsetTop;
            var r2 = l2 + obj2.offsetWidth;
            var b2 = t2 + obj2.offsetHeight;
            if (b1 <= t2 || l1 >= r2 || t1 >= b2 || r1 <= l2) {
                return false;
            } else {
                return true;
            }
        }
        ,
        backPrevStep: function () {
            var prevIndex = this.history.length - 1;
            if (!this.history[prevIndex]) return;
            var perPos = this.history[prevIndex].personPos;
            var boxPos = this.history[prevIndex].boxPos;
            this.history.splice(prevIndex, 1);
            this.oP.x = perPos.x;
            this.oP.y = perPos.y;
            this.oP.style.left = this.oP.x * 50 + 'px';
            this.oP.style.top = this.oP.y * 50 + 'px';
            for (var i = 0; i < boxPos.length; i++) {
                this.boxs[i].style.left = boxPos[i].x + 'px';
                this.boxs[i].style.top = boxPos[i].y + 'px';
            }

        }
        ,
        ini: function (oParent, num) {
            var that = this
            this.num = num;
            this.oParent = oParent;
            var gk = this.gk[num];
            this.createMap(gk);
            var self = this;
            onkeyup = function (ev) {
                var oEvent = ev || event;
                switch (oEvent.keyCode) {
                    case 37://left
                        self.oP.className = 'person2';
                        self.personRun({x: -1, y: 0});
                        break;
                    case 65://left
                        self.oP.className = 'person2';
                        self.personRun({x: -1, y: 0});
                        break;
                    case 38://up
                        self.oP.className = 'person1';
                        self.personRun({x: 0, y: -1});
                        break;
                    case 87://up
                        self.oP.className = 'person1';
                        self.personRun({x: 0, y: -1});
                        break;
                    case 39://right
                        self.oP.className = 'person4';
                        self.personRun({x: 1, y: 0});
                        break;
                    case 68://right
                        self.oP.className = 'person4';
                        self.personRun({x: 1, y: 0});
                        break;
                    case 40://down
                        self.oP.className = 'person3';
                        self.personRun({x: 0, y: 1});
                        break;
                    case 83://down
                        self.oP.className = 'person3';
                        self.personRun({x: 0, y: 1});
                        break;
                    case 81://上一步
                        self.backPrevStep();
                        break;
                }


            }
        }
    }


    //var path = "RDRULLLULURDRULUR";
    $("#autoButton").click(function () {
//        console.log( ))
        var people = {
            "Y": BoxYouXi.oP.x,
            "X": BoxYouXi.oP.y,
        }

        // console.log(BoxYouXi.gk[0])
        console.log(BoxYouXi.boxs)

        $.ajax({
            url: "../getPath",
            dataType: "json",
            type: "post",
            data: {
                "mapInfo": JSON.stringify(BoxYouXi.gk[0].map),
                "boxInfo": JSON.stringify(BoxYouXi.temp_boxs),
                "peopleInfo": JSON.stringify(people)
            },
            success: function (result) {
                if (result.code == '1') {
                    //有解
                    path = result.body.path;
                    var stepCount = result.body.stepCount;
                    var searchCount = result.body.searchCount;
                    alert("找到路径！");
                    //var index = 0;

                    var text = $("#resultShow").html() + "搜寻结果:<br>" + "步数：" + stepCount + "<br>" + "搜寻场景数：" + searchCount;

                    $("#resultShow").html(text);

                    setInvervalCBN(function () {
                        movePeople()
                    }, function () {
                        return 1000
                    })

//                    setTimeout(function () {
//                        if (index < path.length) {
//                            if (path.charAt(index) == 'L') {
//                                //左移
//                                BoxYouXi.oP.className = 'person2';
//                                BoxYouXi.personRun({x: -1, y: 0});
//                            }
//                            if (path.charAt(index) == 'U') {
//                                //上移
//                                BoxYouXi.oP.className = 'person1';
//                                BoxYouXi.personRun({x: 0, y: -1});
//                            }
//                            if (path.charAt(index) == 'R') {
//                                //右移
//                                BoxYouXi.oP.className = 'person4';
//                                BoxYouXi.personRun({x: 1, y: 0});
//                            }
//                            if (path.charAt(index) == 'D') {
//                                //下移
//                                BoxYouXi.oP.className = 'person3';
//                                BoxYouXi.personRun({x: 0, y: 1});
//                            }
//                            index++;
//                        }
//                    }, 1000)
                }
                if (result.code == '0') {
                    //无解
                    alert("无解！");
                }
            }, error: function (result) {
//                setTimeout(function () {
//                    BoxYouXi.oP.className = 'person2';
//                    BoxYouXi.personRun({x: -1, y: 0});
//                }, 1000)
                alert("连接失败！");
            }
        })//$.ajax

    })

    var index = 0;

    function movePeople() {
        console.log(index)
        if (index < path.length) {
            if (path.charAt(index) == 'L') {
                //左移
                BoxYouXi.oP.className = 'person2';
                BoxYouXi.personRun({x: -1, y: 0});
            } else if (path.charAt(index) == 'U') {
                //上移
                BoxYouXi.oP.className = 'person1';
                BoxYouXi.personRun({x: 0, y: -1});
            } else if (path.charAt(index) == 'R') {
                //右移
                BoxYouXi.oP.className = 'person4';
                BoxYouXi.personRun({x: 1, y: 0});
            } else if (path.charAt(index) == 'D') {
                //下移
                BoxYouXi.oP.className = 'person3';
                BoxYouXi.personRun({x: 0, y: 1});
            }
        }
        index += 1
    }

    //var path = "RDRULLLULURDRULUR";

    function setInvervalCBN(fn, fdt) {
        var handle = {id: null}
        var step = function () {
            fn();
            handle.id = setTimeout(step, fdt());
        };
        handle.id = setTimeout(step, fdt());
        return handle;
    }

    BoxYouXi.ini(document.getElementById('yxbox'), 0);
</script>
</body>
</html>
