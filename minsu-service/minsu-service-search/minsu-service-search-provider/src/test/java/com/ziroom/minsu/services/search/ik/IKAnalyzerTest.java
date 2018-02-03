package com.ziroom.minsu.services.search.ik;

import base.BaseTest;
import com.ziroom.minsu.services.solr.common.IKAnalyzerService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>分词</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/12.
 * @version 1.0
 * @since 1.0
 */
public class IKAnalyzerTest extends BaseTest {


    @Resource(name="search.ikAnalyzerService")
    private IKAnalyzerService ikAnalyzerService;


    @Test
    public void TestikAnalysis(){

        String txt = "我喜欢飞翔。我爱毛主席 我叫丁adas%$^%*&%^&^*(@!&#*(&@!#&!(*&#!#_(!#)77789723497982)(*()*()*()*)(*w&w&[P_方文 你说我想干啥啊 法轮功 大纪元 我不喜欢李洪志 我叫丁方文";

        txt = "上帝把两群羊放在草原上，一群在南，一群在北。上帝还给羊群找了两种天敌，一种是狮子，一种是狼。上帝对羊群说:“如果你们要狼，就给一只，任它随意咬你们。如果你们要狮子，就给两头，你们可以在两头狮子中任选一头，还可以随时更换。”  \n" +
                "\n" +
                "这道题的问题就是：如果你也在羊群中，你是选狼还是选狮子？很容易做出选择吧？  \n" +
                "\n" +
                "好吧，记住你的选择，接着往下看。  \n" +
                "\n" +
                "南边那群羊想,狮子比狼凶猛得多,还是要狼吧。于是,它们就要了一只狼。  \n" +
                "\n" +
                "北边那群羊想，狮子虽然比狼凶猛得多，但我们有选择权，还是要狮子吧。于是，它们就要了两头狮子。  \n" +
                "\n" +
                "那只狼进了南边的羊群后，就开始吃羊。狼身体小，食量也小，一只羊够它吃几天了。这样羊群几天才被追杀一次。  \n" +
                "\n" +
                "北边那群羊挑选了一头狮子，另一头则留在上帝那里。这头狮子进入羊群后,也开始吃羊。狮子不但比狼凶猛，而且食量惊人，每天都要吃一只羊。这样羊群就天天 都要被追杀，惊恐万状。羊群赶紧请上帝换一头狮子。不料，上帝保管的那头狮子一直没有吃东西，正饥饿难耐,它扑进羊群，比前面那头狮子咬得更疯狂。羊群一 天到晚只是逃命，连草都快吃不成了。  \n" +
                "\n" +
                "南边的羊群庆幸自己选对了天敌，又嘲笑北边的羊群没有眼光。北边的羊群非常后悔，向上帝大倒苦水,要求更换天敌，改要一只狼。上帝说：“天敌一旦确定，就不能更改,必须世代相随,你们唯一的权利是在两头狮子中选择。”  \n" +
                "\n" +
                "北边的羊群只好把两头狮子骂人的话边的羊群悲惨得多，它们索性不换了,让一头狮子吃得膘肥体壮,另一头狮子则饿得精瘦。眼看那头瘦狮子快要饿死了,羊群才请上帝换一头。  \n" +
                "\n" +
                "这头瘦狮子经过长时间的饥饿后,慢慢悟出了一个道理:自己虽然凶猛异常,一百只羊都不是对手,可是自己的命运是操纵在羊群手里的。羊群随时可以把自己送回 上帝那里,让自己饱受饥饿的煎熬,甚至有可能饿死。想通这个道理后,瘦狮子就对羊群特别客气,只吃死羊和病羊,凡是健康的羊它都不吃了。  \n" +
                "\n" +
                "羊群喜出望外,有几只小羊提议干脆固定要瘦狮子,不要那头肥狮子了。一只老公羊提醒说:“瘦狮子是怕我们送它回上帝那里挨饿,才对我们这么好。万一肥狮子 饿死了,我们没有了选择的余地,瘦狮子很快就会恢复凶残的本性。”羊群觉得老羊说得有理,为了不让另一头狮子饿死,它们赶紧把它换回来。  \n" +
                "\n" +
                "原先膘肥体壮的那头狮子,已经饿得只剩下皮包骨头了,并且也懂得了自己的命运是操纵在羊群手里的道理。为了能在草原上待久一点,它竟百般讨好起羊群来。而那头被送交给上帝的狮子,则难过得流下了眼泪。北边的羊群在经历了重重磨难后,终于过上了自由自在的生活。  \n" +
                "\n" +
                "南边的那群羊的处境却越来越悲惨了,那只狼因为没有竞争对手,羊群又无法更换它,它就胡作非为,每天都要咬死几十只羊,这只狼早已不吃羊肉了,它只喝羊心里的血。它还不准羊叫,哪只叫就立刻咬死哪只。南边的羊群只能在心中哀叹:“早知道这样，还不如要两头狮子。”";

        txt="我爱中华人民共和国 擦 操 日你妹 什么  几把 ";

        //使用智能分詞
        List<String> ikAnalyseResult = ikAnalyzerService.ikAnalysis(txt,true);
        for (String ikRs : ikAnalyseResult) {
            System.out.print(ikRs+"|");
        }
        System.out.println("\n=================================");

        //使用細粒度分詞
        List<String> ikAnalyseResult2 = ikAnalyzerService.ikAnalysis(txt,false);
        for (String ikRs : ikAnalyseResult2) {
            System.out.print(ikRs+"|");
        }

        System.out.println("==================================");

    }



    @Test
    public void testGetIKAnalyzerResult(){
        //使用智能分詞
        List<String> ikAnalyseResult = ikAnalyzerService.ikAnalysis("努力工作，方能快速成长,丁丁租房，链家地产，美欣家园",true);
        for (String ikRs : ikAnalyseResult) {
            System.out.print(ikRs+"|");
        }
        System.out.println("\n=================================");

        //使用細粒度分詞
        List<String> ikAnalyseResult2 = ikAnalyzerService.ikAnalysis("努力工作，方能快速成长,丁丁租房，链家地产，美欣家园，莱圳家园，益城家园，君安家园",false);
        for (String ikRs : ikAnalyseResult2) {
            System.out.print(ikRs+"|");
        }

        System.out.println("==================================");

    }


    @Test
    public void aaaaaaa(){

        String txt="王进发\n" +
                "王军涛\n" +
                "王力雄\n" +
                "王瑞林\n" +
                "王润生\n" +
                "王若望\n" +
                "王松昌\n" +
                "王韦景\n" +
                "王维林\n" +
                "王希哲\n" +
                "王秀丽\n" +
                "王冶坪\n" +
                "隗福临\n" +
                "尉健行\n" +
                "慰安妇\n" +
                "魏京生\n" +
                "魏新生\n" +
                "温宝宝\n" +
                "温家宝"+
                "边缘化\n" +
                "边缘原始细胞\n" +
                "边缘地\n" +
                "边缘增强\n" +
                "边缘学科\n" +
                "边缘封闭区\n" +
                "边缘嵴\n" +
                "边缘性\n" +
                "边缘性龈炎\n" +
                "边缘效应\n" +
                "边缘波动\n" +
                "边缘海\n" +
                "边缘种植\n" +
                "边缘科学\n" +
                "边缘穿孔卡";
        //使用智能分詞
        List<String> ikAnalyseResult = ikAnalyzerService.ikAnalysis(txt,false);
        for (String ikRs : ikAnalyseResult) {
            System.out.print(ikRs+"|");
        }
        System.out.println("\n=================================");


    }



}
