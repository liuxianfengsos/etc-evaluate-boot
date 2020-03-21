package org.jeecg.modules.hrbetc.exam.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.hrbetc.exam.entity.EtcQuestion;
import org.jeecg.modules.hrbetc.exam.service.IEtcQuestionService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: etc_question
 * @Author: jeecg-boot
 * @Date:   2020-03-11
 * @Version: V1.0
 */
@Api(tags="etc_question")
@RestController
@RequestMapping("/exam/etcQuestion")
@Slf4j
public class EtcQuestionController extends JeecgController<EtcQuestion, IEtcQuestionService> {
	@Autowired
	private IEtcQuestionService etcQuestionService;
	private List<EtcQuestion> list = new ArrayList<>();
	private Integer scores = 0;//总分
	//处理试题提交；
	@ResponseBody
	@PostMapping(value = "/mysubmit")
	public void myanswer(@RequestBody JSONObject jsonObject,HttpServletRequest req){
		System.out.println("66666666666666666666666666666"+jsonObject);
		JSONArray JArray = jsonObject.getJSONArray("list");
		List<EtcQuestion> list = JArray.toJavaList(EtcQuestion.class);

		for (EtcQuestion e:list){
			EtcQuestion etcQuestion = etcQuestionService.getById(e.getId());
			if(etcQuestion.getAnswer().equals(e.getAnswer())){
				scores += 10;
			}
			//System.out.println(e.getId()+"--"+e.getAnswer());
		}
		System.out.println("获得的分数"+scores);
		HttpSession session = req.getSession();
	}
	/*@ResponseBody
	@GetMapping("/getmyreturnstr")
	public String getResult(){
		String returnstr;
		if(scores>80){
			returnstr = "第一部分 报告导语 【测评报告简介】 为了使你能更好的使用本测评报告，请你仔细阅读本测评报告的所有内容。 本报告将为你 介绍在此次报告中提到的理论基础和相关名词以及为你解读属于你的测评报告结果。 通过 本测评，可以相对准确地帮助你了解你自身的性格特征与职业选择之间的匹配关系， 同时 也为你选择未来的职业发展方向提供一定的指导和参考依据。 ■ 本测试主要以霍兰德职业兴趣理论（Holland Vocational Interest Theory）为基础， 结合每种职业兴趣类型的性格特点，并根据中国职业划分的基本类型设计而成。 ■ 本测试主要从你的活动倾向性、你的职业能力评估以及你的职业性格倾向性这三个方面 来对你的职业兴趣类型进行测评。这三个方面的内容是相辅相成的， 当你想要从事某一职 业时，你不仅需要结合自身过去从事某活动的兴趣程度，也要考虑你是否已经具备了从事这 一职业的能力。 同时，职业的发展也不能忽视自身的职业性格倾向性，因此在本测试中有 一部分测试题是对你的职业性格进行测试。 通过这三个方面的测试，将有助于为你提供更 为准确的测评结果。 ■ 在本测试中，将个体的职业兴趣类型划分为：操作/技术型（R）、研究型（I）、艺术 型（A）、社会服务型（S）、进取型（E）、 常规事物型（C）这六大类型。这六种职业兴 趣类型都有着不同的职业兴趣特征以及所对应的适合的职业。 当我们进行职业选择时，如 果能够结合自身的个性特点以及适合自己的职业兴趣类型，并利用好自己的职业性格优势， 弥补或避免自己的职业性格劣势，将更有助于你在职业发展中获得更多的满意感和成就感。\n" +
					"职业兴趣测评结果报告 【报告阅读指导】 报告中相关名词的介绍： ■ 职业兴趣类型图：在职业兴趣类型图中，将呈现你在测试中得分最高的前三项 的职业兴趣类型，当你某一类型的得分越高时，雷达图中所对应的字母类型尖角也 就越突出。 ■ 职业兴趣类型编码。报告中的字母为对应的职业兴趣类型编码，R为操作/技术 型、I为研究型、A为艺术型、S为社会服务型、E为进取型、C为常规事物型。 在本 报告中只呈现与你有关的职业兴趣类型解读。 ■ 职业推荐：在职业推荐部分，将给你推荐适合的职业。 ■ 职业性格：这一部分主要从“你的职业核心词、你最享受的事情、你的人生目 标、你的价值观、你不喜欢的事物、你的最佳竞争力”这五个方面来描述你的职业 性格。 ■ 职业性格优劣势：这一部分主要对你的职业性格优劣势进行描述，将帮助你了 解你在职业发展中你的优势和劣势，使你在职业道路中能够获得更好的发展。 ■ 适合的工作环境：这一部分是基于你的职业性格特征来进行推荐，如果你能在 适合你的工作环境中工作，将更有助于提高你对所从事工作的满意度，也将更有助 于你的职业发展。 声明：以上结论及建议只是基于你的测验结果，可能会因未能收集你更多的信息导 致报告结果与你的实际状态不完全相符，请根据你的具体情况加以取舍";

			JSONObject jsonObject = JSONObject.parseObject(returnstr);

		}else if(scores>60){
			returnstr = "【职业兴趣类型详解】 C（常规事务型） 主要特点：顺从、坦率、谦虚、坚毅、稳重、注重实际； 比较喜欢现实、实在以及规则明确的工作，偏爱与具体事务打交道以及从事有规则 的、明确的、有序的、系统性的工作，如以机械和物为对象的技能性或技术性的事 物；R（操作/技术型） 主要特点：分析、好奇，见解独立，条理清晰、善于脑力劳动； 喜欢观察分析以及采用理性思考的方式探究事务； 擅长通过逻辑推理、理论分析或实验发现来解决问题； 喜欢从事具有创造性和挑战性的工作，偏好从事研究类职业活动，重视科学，但缺 乏领导方面的才能。 I（研究型） 主要特点：顺从、谨慎、保守、规律、坚毅、稳重、注重效率； 喜欢规范明确、秩序井然的工作环境； 做事仔细、认真、循规蹈矩，更愿意从事有规律性的工作； 不希望工作内容或工作环境有过多的变化，善于从事系统且有条理的工作，但缺乏 创造力和应变能力。 北京易普斯咨询有限责任公司拥有本报告所有版权，未经易普斯咨询书面许可，不得复制、传播\n" +
					"职业兴趣测评结果报告 【你的职业性格】 职业核心词 ■自然的、真实的、现实的 ■分析的、批判性的、好奇心 ■仔细的、有秩序的、顺从的 最享受的事情 ■通过手动操作的工具、装置 ■阅读和思考问题的解决方案 ■记录、计算 人生目标 ■为科学做出技术贡献 ■为科学理论和知识做出贡献 ■高效率的工作以及保持精确性 价值观 ■自律、诚实 ■智力、逻辑、成就和知识 ■舒适的生活、准确、彬彬有礼、节俭 不喜欢的事物 ■与人相关的事物 ■领导和劝服他人 ■艺术创作 北京易普斯咨询有限责任公司拥有本报告所有版权，未经易普斯咨询书面许可，不得复制、传播\n" +
					" 职业兴趣测评结果报告 最佳竞争力 ■机械 ■科学 ■商业";
		}else{
			returnstr = "【职业性格优势】 ■集中精力的专注于自己的某一项工作的完成；一丝不苟、认真专注地对待要解决 的问题；高度的责任心，信守诺言，值得信任； ■能灵活地适应新情况，具有随机应变的能力；独立自主、探险精神、创造意识以 及克服困难的勇气；具有强烈的好奇心，具有快速搜索需要的信息的能力； ■尊重别人的地位和能力；讲求实效的工作态度，办事方法现实可行； 【职业性格劣势】 ■可能过于理智而失去了享受生活的乐趣；不喜变动，对于经常变化的事物感到不 安；过于注重细节而忽略了整体以及对事物的长远影响； ■不擅长把复杂的思想和问题用简明的形式表现出来；对待他人的要求和对待自己 的要求一样高；不轻易给自己正面、肯定的评价； ■不太愿意尝试、接受新的和未经考验的观点和想法；在感到压力比较大时，可能 难以按照以往的沉着和冷静来进行应对； 北京易普斯咨询有限责任公司拥有本报告所有版权，未经易普斯咨询书面许可，不得复制、传播\n" +
					"Page 9 职业兴趣测评结果报告 【适合的工作环境】 ■适合于能够依照过去的经验和规律来解决问题；需要更多独立的空间，专心的去 解决问题或完成整个项目； ■适合于需要运用智力、能够独立工作的工作环境，偶尔有一些冒险和变动是你能 够接受的； ■适合于具有结构的、有系统、有条理的工作环境；";
		}
		return returnstr;
	}*/

	 @AutoLog(value = "etc_question-分页列表查询")
	 @ApiOperation(value="etc_question-分页列表查询", notes="etc_question-分页列表查询")
	 @GetMapping(value = "/list4exam")
	 public Result<?> queryPageList4Exam(EtcQuestion etcQuestion,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<EtcQuestion> queryWrapper = QueryGenerator.initQueryWrapper(etcQuestion, req.getParameterMap());
		 Page<EtcQuestion> page = new Page<EtcQuestion>(pageNo, pageSize);
		 IPage<EtcQuestion> pageList = etcQuestionService.page(page, queryWrapper);
		 //List<EtcQuestion> pageList = etcQuestionService.list();
		 Integer index = 1;
		 for(EtcQuestion e:pageList.getRecords()){
		// for(EtcQuestion e:pageList){
			 EtcQuestion etcq = new EtcQuestion();
			 String id = e.getId();
			String qtName = e.getQtName();
			String opta = e.getOpta();
			 String optb = e.getOptb();
			 String optc = e.getOptc();
			 String optd = e.getOptd();

			 etcq.setId(id);
			 etcq.setQtName(qtName);
			 etcq.setOpta(opta);
			 etcq.setOptb(optb);
			 etcq.setOptc(optc);
			 etcq.setOptd(optd);
			 etcq.setMark(Integer.toString(index));
			 etcq.setAnswer(null);
			 index++;
			 list.add(etcq);
		 }
		// list.stream().forEach(System.out::println);
		 return Result.ok(list);
		 //return Result.ok(pageList);
	 }


	/**
	 * 分页列表查询
	 *
	 * @param etcQuestion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "etc_question-分页列表查询")
	 @ApiOperation(value="etc_question-分页列表查询", notes="etc_question-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(EtcQuestion etcQuestion,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<EtcQuestion> queryWrapper = QueryGenerator.initQueryWrapper(etcQuestion, req.getParameterMap());
		 Page<EtcQuestion> page = new Page<EtcQuestion>(pageNo, pageSize);
		 IPage<EtcQuestion> pageList = etcQuestionService.page(page, queryWrapper);
		 return Result.ok(pageList);
	 }
	
	/**
	 *   添加
	 *
	 * @param etcQuestion
	 * @return
	 */
	@AutoLog(value = "etc_question-添加")
	@ApiOperation(value="etc_question-添加", notes="etc_question-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EtcQuestion etcQuestion) {
		etcQuestionService.save(etcQuestion);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param etcQuestion
	 * @return
	 */
	@AutoLog(value = "etc_question-编辑")
	@ApiOperation(value="etc_question-编辑", notes="etc_question-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EtcQuestion etcQuestion) {
		etcQuestionService.updateById(etcQuestion);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "etc_question-通过id删除")
	@ApiOperation(value="etc_question-通过id删除", notes="etc_question-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		etcQuestionService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "etc_question-批量删除")
	@ApiOperation(value="etc_question-批量删除", notes="etc_question-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.etcQuestionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "etc_question-通过id查询")
	@ApiOperation(value="etc_question-通过id查询", notes="etc_question-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		EtcQuestion etcQuestion = etcQuestionService.getById(id);
		if(etcQuestion==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(etcQuestion);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param etcQuestion
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EtcQuestion etcQuestion) {
        return super.exportXls(request, etcQuestion, EtcQuestion.class, "etc_question");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EtcQuestion.class);
    }

}
