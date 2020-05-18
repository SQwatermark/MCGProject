//配置项
//一个空间至少需要一个基础怪，否则程序会出错
var m_刷怪空间 = {
    生成区域: {
        魔法森林刷怪区: {
            pos1: [-800, 2608],
            pos2: [2264, 1156],
            npc最大数量: 150,
            生成列表: {//内容名字任意
                //恐慌的野生动物
                白毛兔: {
                    tab: 6,
                    name: "白毛兔",
                    max: 60,//最多几个
                    weight: 8,//权重            
                },
                黑毛兔: {
                    tab: 6,
                    name: "黑毛兔",
                    max: 60,//最多几个
                    weight: 8,//权重            
                },
                褐毛兔: {
                    tab: 6,
                    name: "褐毛兔",
                    max: 60,//最多几个
                    weight: 8,//权重            
                },
                野鸡: {
                    tab: 6,
                    name: "野鸡",
                    max: 60,//最多几个
                    weight: 12,//权重            
                },
                //会反击的怪物
                妖精1: {
                    tab: 6,
                    name: "小妖精",
                    max: 200,//最多几个
                    weight: 100,//权重            
                },
                妖精3: {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "森之妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精6:
                {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "花之妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精7:
                {
                    max: 10,//最多几个
                    weight: 4,//权重
                    tab: 6,
                    name: "黑发女仆妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精精英1:
                {
                    max: 3,//最多几个
                    weight: 2,//权重
                    tab: 6,
                    name: "蝴蝶妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精精英2:
                {
                    max: 3,//最多几个
                    weight: 0.3,//权重
                    tab: 6,
                    name: "莉莉白",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },                
                毛玉1:
                {
                    max: 300,//最多几个
                    weight: 50,//权重
                    tab: 6,
                    name: "小毛玉",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                毛玉2:
                {
                    max: 50,//最多几个
                    weight: 50,//权重
                    tab: 6,
                    name: "毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                },
                毛玉23:
                {
                    max: 50,//最多几个
                    weight: 10,//权重
                    tab: 6,
                    name: "粉毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                }, 
                森之毛玉:
                {
                    max: 50,//最多几个
                    weight: 30,//权重
                    tab: 6,
                    name: "森之毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                }, 
                毒之毛玉:
                {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "毒之毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                },                                                      
                大毛玉1:
                {
                    max: 3,//最多几个
                    weight: 2,//权重
                    tab: 6,
                    name: "大毛玉",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
               巨大红色蘑菇:
                {
                    max: 30,//最多几个
                    weight: 5,//权重
                    tab: 6,
                    name: "巨大红色蘑菇",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
               巨大紫色蘑菇:
                {
                    max: 30,//最多几个
                    weight: 5,//权重
                    tab: 6,
                    name: "巨大紫色蘑菇",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
               巨大黑色蘑菇:
                {
                    max: 30,//最多几个
                    weight: 2,//权重
                    tab: 6,
                    name: "巨大黑色蘑菇",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                树精:
                {
                    max: 30,//最多几个
                    weight: 10,//权重
                    tab: 6,
                    name: "树精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                }, 
                蘑菇娘:
                {
                    max: 10,//最多几个
                    weight: 0.2,//权重
                    tab: 6,
                    name: "蘑菇娘",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },                                                
            },
            cutlist: [],//系统字段，无需赋值，最好每个里面都写一下
            entitylist: [],//系统字段，无需赋值，最好每个里面都写一下
        },
        人里周遭刷怪区: {
            pos1: [294, 99],
            pos2: [2712, 974],
            npc最大数量: 150,
            生成列表: {//名字任意
                //恐慌的野生动物
                白毛兔: {
                    tab: 6,
                    name: "白毛兔",
                    max: 60,//最多几个
                    weight: 8,//权重            
                },
                黑毛兔: {
                    tab: 6,
                    name: "黑毛兔",
                    max: 60,//最多几个
                    weight: 8,//权重            
                },
                褐毛兔: {
                    tab: 6,
                    name: "褐毛兔",
                    max: 60,//最多几个
                    weight: 8,//权重            
                },
                野鸡: {
                    tab: 6,
                    name: "野鸡",
                    max: 60,//最多几个
                    weight: 12,//权重            
                },
                //会反击的怪物
                妖精1: {
                    tab: 6,
                    name: "小妖精",
                    max: 200,//最多几个
                    weight: 100,//权重            
                },
                妖精3: {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "森之妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精4:
                {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "火之妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精5:
                {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "湖之妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精6:
                {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "花之妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精7:
                {
                    max: 10,//最多几个
                    weight: 4,//权重
                    tab: 6,
                    name: "黑发女仆妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精精英1:
                {
                    max: 3,//最多几个
                    weight: 2,//权重
                    tab: 6,
                    name: "蝴蝶妖精",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                妖精精英2:
                {
                    max: 3,//最多几个
                    weight: 0.3,//权重
                    tab: 6,
                    name: "莉莉白",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },                
                毛玉1:
                {
                    max: 300,//最多几个
                    weight: 400,//权重
                    tab: 6,
                    name: "小毛玉",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                毛玉2:
                {
                    max: 50,//最多几个
                    weight: 200,//权重
                    tab: 6,
                    name: "毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                },
                毛玉23:
                {
                    max: 50,//最多几个
                    weight: 20,//权重
                    tab: 6,
                    name: "粉毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                },                
                毛玉4:
                {
                    max: 30,//最多几个
                    weight: 30,//权重
                    tab: 6,
                    name: "玄武泽毛玉",
                    spawnDelay: -1//npc少于指定数量时开始重新生成的延时
                },
                大毛玉1:
                {
                    max: 3,//最多几个
                    weight: 2,//权重
                    tab: 6,
                    name: "大毛玉",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                大毛玉2:
                {
                    max: 3,//最多几个
                    weight: 0.8,//权重
                    tab: 6,
                    name: "玄武泽大毛玉",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                },
                大蝙蝠:
                {
                    max: 3,//最多几个
                    weight: 0.8,//权重
                    tab: 6,
                    name: "大蝙蝠",
                    spawnDelay: -1,//npc少于指定数量时开始重新生成的延时
                }
            },
            cutlist: [],//系统字段，无需赋值，最好每个里面都写一下
            entitylist: [],//系统字段，无需赋值，最好每个里面都写一下
        },
    },
    黑名单区域: {
        人里整体: {    //名字随便起
            pos1: [434, 48],
            pos2: [1612, 878],
            delete: true
        },
        人里门口: {
            pos1: [921, 855],
            pos2: [1018, 930],
            delete: true
        },
        爱丽丝家: {    //名字随便起
            pos1: [794, 1709],
            pos2: [627, 1863],
            delete: true
        },
        魔理沙家: {
            pos1: [-280, 1112],
            pos2: [-357, 1191],
            delete: true
        },
        魔法使家: {
            pos1: [-236, 2336],
            pos2: [-378, 2425],
            delete: true
        },
        香霖堂: {
            pos1: [1914, 610],
            pos2: [1828, 521],
            delete: true
        },
        神社附近: {
            pos1: [2688, 407],
            pos2: [2568, 222],
            delete: true
        },
    }
};
//以玩家为中心的刷怪最大值和最小值
var m_刷怪距离 = {//半径值
    min: 12,
    max: 36,
}
//20tick为1秒
var m_刷怪触发间隔 = 20 * 3;
//字段
var block;
var timers;
var world;
var baka_zat;
var Muppet_cat;
var _event;//临时字段

//hook
function init(event) {
    _event = event;
    block = event.block;
    timers = block.getTimers();
    world = block.getWorld();
    timers.clear();
    baka_zat = world.getPlayer("baka_zat");
    //重构区域的内容
    for (var i in m_刷怪空间.生成区域) {
        var _生成区域 = m_刷怪空间.生成区域[i];
        func_修正坐标(_生成区域.pos1, _生成区域.pos2);
        //给拆分组赋初值
        _生成区域.cutlist = [Copy({ pos1: _生成区域.pos1, pos2: _生成区域.pos2 })];
        _生成区域.entitylist = [];
    } for (var i in m_刷怪空间.黑名单区域) {
        var area = m_刷怪空间.黑名单区域[i];
        func_修正坐标(area.pos1, area.pos2);
    }
    for (var i in m_刷怪空间.生成区域) {    //遍历刷怪区域,并用算法和黑名单区域拆碎
        var _生成区域 = m_刷怪空间.生成区域[i];
        for (var j in m_刷怪空间.黑名单区域) {  //遍历黑名单区域
            var _黑名单区域 = m_刷怪空间.黑名单区域[j];
            var _拆分组 = Copy(_生成区域.cutlist);//从生成区域里提取出上一遍历的拆分组然后复位成空            
            _生成区域.cutlist.length = 0;
            for (var k in _拆分组) {//判断每块是否被黑名单切到
                var _拆分块 = _拆分组[k];
                if (func_区块接触(_拆分块, _黑名单区域)) {//生成区域和黑名单区域有接触                    
                    var _新拆分块 = func_拆解区块(_拆分块, _黑名单区域);
                    for (var _i in _新拆分块) {
                        _生成区域.cutlist.push(_新拆分块[_i]);
                    }
                } else {//把不接触黑名单的区块放回去                    
                    _生成区域.cutlist.push(_拆分块);
                }
            }
        }
    }

    //临时关闭脉冲
    timers.forceStart(129, m_刷怪触发间隔, true);//开启线程    
}
function interact(event) {
    func_刷一个怪(event);
}
function timer(event) {
    if (event.id == 129) {
        if (func_x分之一的几率(1) || true) {//1分之一的几率会刷怪            
            func_刷一个怪(event);
        }
    }
}

function func_刷一个怪(e) {
    var players = func_洗牌(world.getAllPlayers());//乱序玩家                        
    var success = false;
    for (var i = 0; i < players.length; i++) {//遍历玩家，玩家周围无法刷怪就下一个玩家
        var player = players[i];
        var ppos = [player.getPos().getX(), player.getPos().getZ()];
        var _玩家外径 = {
            pos1: [ppos[0] - m_刷怪距离.max, ppos[1] - m_刷怪距离.max],
            pos2: [ppos[0] + m_刷怪距离.max, ppos[1] + m_刷怪距离.max]
        };
        var _玩家内径 = {
            pos1: [ppos[0] - m_刷怪距离.min, ppos[1] - m_刷怪距离.min],
            pos2: [ppos[0] + m_刷怪距离.min, ppos[1] + m_刷怪距离.min]
        };
        var _玩家拆分组 = func_拆解区块(_玩家外径, _玩家内径);//返回一组回型被拆开的区块
        //这个接触判断包含了拆分组信息
        var _接触区组数据组 = func_取得所有和玩家接触的区块数据(_玩家外径, _玩家拆分组);
        //玩家附近没有能接触的刷怪区块，就下一个玩家
        if (_接触区组数据组.length == 0) { continue; }
        var _随机区块数据 = func_随机元素(_接触区组数据组);
        var _碎块 = func_取得所有重叠区块(_玩家拆分组, _随机区块数据.cuts);
        var _随机刷怪坐标 = func_从碎块里取得随机坐标(_碎块);
        //Say("随机刷怪坐标" + toS(_随机刷怪坐标));
        var rpos3D = func_取得地面坐标(_随机刷怪坐标[0], _随机刷怪坐标[1]);
        //玩家地形险恶，255撞顶，换个人刷吧，此处浪费了一些性能
        if (rpos3D === false) { continue; }
        var _随机怪 = func_随机怪(_随机区块数据.owner.生成列表);
        //生成怪
        var entity = e.API.getClones().spawn(rpos3D[0], rpos3D[1], rpos3D[2], _随机怪.tab, _随机怪.name, world);
        //_随机区块数据.owner.entitylist.push(entity);
        success = true;
        break;
        //如果成功就跳出循环
    } if (success) {
    } else {
    }
}

//封装1：使用脚本字段的方法
function func_取得所有和玩家接触的区块数据(pmax, pcuts) {
    //不粗略，如果有接触就会进入精细判断模式    
    var list = [];
    for (var key in m_刷怪空间.生成区域) {
        var _刷怪区 = m_刷怪空间.生成区域[key];
        var _刷怪区数据包 = { owner: _刷怪区, cuts: [] };
        //玩家至少和区域粗略接触，但具体不明，可以位于黑名单中央，这种情况就不在区域内
        if (func_区块接触(pmax, _刷怪区)) {
            for (var i in pcuts) {//首先以玩家拆分块为基础遍历
                for (var j in _刷怪区.cutlist) {//这里遍历碎块                  
                    //如果玩家碎块和区域碎块接触并且数组里还没这个碎块
                    var _刷怪拆分块 = _刷怪区.cutlist[j];
                    if (func_区块接触(pcuts[i], _刷怪拆分块)
                        && _刷怪区数据包.cuts.indexOf(_刷怪拆分块) == -1) {
                        _刷怪区数据包.cuts.push(_刷怪区.cutlist[j]);//把接触的新区块碎块放入数组
                    }
                }
            } if (_刷怪区数据包.cuts.length > 0) {//有接触的碎块存在，加入数组
                list.push(_刷怪区数据包);
            }
        }
    }
    return list;
}
function func_取得地面坐标(x, z) {
    var y = 1;
    for (var i = 0; i < 255; i++) {
        if (world.getBlock(x, y, z).getName() == "minecraft:air") {
            return [x, y, z];
        }
        y++;
    }
    return false;
}
function func_随机怪(list) {//list非数组
    var counter = 0;
    var dir = {};
    for (var key in list) {
        counter += list[key].weight;
        dir[counter] = list[key];
    }
    var rannum = Math.floor(Math.random() * counter);
    for (var key in dir) {
        if (rannum < key) {
            return dir[key];
        }
    }
}
//封装1.5层（类似底层，不使用字段，但定制）
function func_从碎块里取得随机坐标(as) {
    //所有碎块的面积即是权重    
    var counter = 0;
    var dir_a = {};
    for (var i = 0; i < as.length; i++) {
        var _面积 = func_面积(as[i]);//加入面积    
        if (_面积 <= 0) {
            Say("有-体积空间区块混入列表" + toS(as[i]));
        }
        counter += _面积;
        dir_a[counter] = as[i];
    }
    var rannum = Math.floor(Math.random() * counter);
    for (var key in dir_a) {
        if (rannum < key) {
            var rana = dir_a[key];
            return func_随机坐标(rana);
        }
    }
    Say("总体积是，随机数是" + counter + " " + rannum);
}
function func_取得所有重叠区块(as, bs) {
    //取得区块组和区块组之间的重叠
    var list = [];
    for (var i = 0; i < as.length; i++) {
        var a = as[i];
        for (var j = 0; j < bs.length; j++) {
            var b = bs[j];
            var a1 = Copy(a.pos1), a2 = Copy(a.pos2);
            var b1 = b.pos1, b2 = b.pos2;//b没操作无需克隆
            if (a1[0] < b1[0]) a1[0] = b1[0];
            if (a2[0] > b2[0]) a2[0] = b2[0];
            if (a1[1] < b1[1]) a1[1] = b1[1];
            if (a2[1] > b2[1]) a1[0] = b1[0];
            if (a2[0] - a1[0] > 0 && a2[1] - a1[1] > 0) list.push({ pos1: a1, pos2: a2 });
        }
    }
    return list;
}
//封装底层
function func_区块接触(a, b) {//1是自身，2是外部  
    var a1 = a.pos1, a2 = a.pos2;
    var b1 = b.pos1, b2 = b.pos2;
    if (a2[0] <= b1[0]) return false;
    if (a1[0] >= b2[0]) return false;
    if (a1[1] >= b2[1]) return false;
    if (a2[1] <= b1[1]) return false;
    return true;
}
function func_拆解区块(a, b) {  //a是被拆
    var a1 = Copy(a.pos1), a2 = Copy(a.pos2);
    var b1 = Copy(b.pos1), b2 = Copy(b.pos2);
    var list = [];
    if (a1[0] < b1[0]) {
        list.push({ pos1: Copy(a1), pos2: [b1[0], a2[1]] });
        a1[0] = b1[0];
    }
    if (a2[0] > b2[0]) {
        list.push({ pos1: [b2[0], a1[1]], pos2: Copy(a2) });
        a2[0] = b2[0];
    }
    if (a1[1] < b1[1]) {
        list.push({ pos1: Copy(a1), pos2: [a2[0], b1[1]] });
        a1[1] = b1[1];
    }
    if (a2[1] > b2[1]) {
        list.push({ pos1: [a1[0], b2[1]], pos2: Copy(a2) });
        a1[1] = b1[1];
    }
    return list;
}
function func_修正坐标(pos1, pos2) {
    //将不规则的两个坐标组成的空间扭成小坐标和大坐标
    var min, max;
    for (var i = 0; i < 2; i++) {
        min = Math.min(pos1[i], pos2[i]);
        max = Math.max(pos1[i], pos2[i]);
        pos1[i] = min;
        pos2[i] = max;
    }
}
function func_面积(a) {
    return (a.pos2[0] - a.pos1[0]) * (a.pos2[1] - a.pos1[1]);
}
function func_随机坐标(a) {
    var rx = Math.floor(Math.random() * (a.pos2[0] - a.pos1[0]));
    var rz = Math.floor(Math.random() * (a.pos2[1] - a.pos1[1]));
    return [rx + a.pos1[0], rz + a.pos1[1]];
}
function func_x分之一的几率(x) {
    return Math.floor(Math.random() * x) == 0;
}
function func_洗牌(input) {
    for (var i = input.length - 1; i >= 0; i--) {

        var randomIndex = Math.floor(Math.random() * (i + 1));
        var itemAtIndex = input[randomIndex];

        input[randomIndex] = input[i];
        input[i] = itemAtIndex;
    }
    return input;
}
function func_随机元素(array) {
    return array[Math.floor(Math.random() * array.length)];
}
function Copy(obj) {
    return JSON.parse(JSON.stringify(obj));
}
function Say(msg) {
    if (baka_zat != null) {
        baka_zat.message(msg);
        //world.playSoundAt(baka_zat.getPos(), "minecraft:entity.player.levelup", 1.0, 1.0);
    }
}
function toS(obj) {
    return JSON.stringify(obj);
}
