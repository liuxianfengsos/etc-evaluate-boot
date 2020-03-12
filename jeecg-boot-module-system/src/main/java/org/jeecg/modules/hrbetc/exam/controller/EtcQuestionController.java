package org.jeecg.modules.hrbetc.exam.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
