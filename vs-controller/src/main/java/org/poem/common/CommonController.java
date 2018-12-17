package org.poem.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.poem.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author poem
 */
@RestController
@RequestMapping
@Api(value = "测试", tags = {"00-测试"})
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/common",method = RequestMethod.GET)
    @ApiOperation(value = "接口",tags = {"00-测试"},httpMethod = "GET")
    public ResultVo<CommonVO> common(){
        CommonVO commonVO = this.commonService.common();
        return new ResultVo<>(0, commonVO, "操作完成");
    }

}
