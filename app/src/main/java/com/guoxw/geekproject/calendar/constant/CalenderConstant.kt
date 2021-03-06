package com.guoxw.geekproject.calendar.constant


/**
* @auther gxw
* @date 17-9-6
* @package ${PACKAGE_NAME}
* @desciption
*/
object CalenderConstant {
    val drinks = arrayOf("水", "茶", "红茶", "绿茶", "咖啡", "奶茶", "可乐", "牛奶", "豆奶", "果汁", "果味汽水", "苏打水", "运动饮料", "酸奶", "酒")

    val directions = arrayOf("北方", "东北方", "东方", "东南方", "南方", "西南方", "西方", "西北方")

    var tools = arrayOf("Eclipse写程序", "MSOffice写文档", "记事本写程序", "Windows8", "Linux", "MacOS", "IE", "Android设备", "iOS设备")
    var varNames = arrayOf("jieguo", "huodong", "pay", "expire", "zhangdan", "every", "free", "i1", "a", "virtual", "ad", "spider", "mima", "pass", "ui")


    val name = arrayOf("写单元测试", "洗澡", "锻炼一下身体", "抽烟", "白天上线", "重构", "使用%t", "跳槽", "招人", "面试", "提交辞职申请", "申请加薪", "晚上加班", "在妹子面前吹牛", "撸管", "浏览成人网站", "命名变量\"%v\"", "写超过%l行的方法", "提交代码", "代码复审", "开会", "打dota", "晚上上线", "修复BUG", "设计评审", "需求评审", "上微博", "上AB站")
    private val good = arrayOf("写单元测试将减少出错", "你几天没洗澡了？", "", "抽烟有利于提神，增加思维敏捷", "今天白天上线是安全的", "代码质量得到提高", "你看起来更有品位", "该放手时就放手", "你遇到千里马的可能性大大增加", "面试官今天心情很好", "公司找到了一个比你更能干更便宜的家伙，巴不得你赶快滚蛋", "老板今天心情很好", "晚上是程序员精神最好的时候", "改善你矮穷挫的形象", "避免缓冲区溢出", "重拾对生活的信心", "", "你的代码组织的很好，长一点没关系", "遇到冲突的几率是最低的", "发现重要问题的几率大大增加", "写代码之余放松一下打个盹，有益健康", "你将有如神助", "晚上是程序员精神最好的时候", "你今天对BUG的嗅觉大大提高", "设计评审会议将变成头脑风暴", "", "今天发生对BUG的嗅觉将变成头脑风暴", "需求全部砍掉", "今天发生的事不能错过", "还需要理由吗？")
    private val bad = arrayOf("写单元测试会降低你的开发效率", "会把设计方面的灵感洗掉", "能量没消耗多少，吃得却更多", "除非你活够了，死得早点没关系", "可能导致灾难性后果", "你很有可能会陷入泥潭", "别人会觉得你在装逼", "鉴于当前的经济形势，你的下一份工作未必比现在强", "你只会招到一两个混饭吃的外行", "面试官不爽，会拿你出气", "鉴于当前的经济形势，你的下一份工作未必比现在强", "公司正在考虑裁员", "晚上是程序员精神最好的时候", "会被识破", "小撸怡情，大撸伤身，强撸灰飞烟灭", "你会心神不宁", "", "你的代码将混乱不堪，你自己都看不懂", "你遇到的一大堆冲突会让你觉得自己是不是时间穿越了", "你什么问题都发现不了，白白浪费时间", "你会被扣屎盆子背黑锅", "你会被虐的很惨", "你白天已经筋疲力尽了", "新产生的BUG将比修复的更多", "人人筋疲力尽，评审就这么过了", "会有很多新的需求", "会被老板看到", "会被老板看到")

    fun getActivities(): MutableList<Map<String, String>> {
        val activities = ArrayList<Map<String, String>>()

        for (i in 0 until name.size - 1) {
            val actMap = HashMap<String, String>()
            actMap.put("name", name[i])
            actMap.put("good", good[i])
            actMap.put("bad", bad[i])
            activities.add(actMap)
        }
        return activities
    }

}

