package com.dg.cloud.fast.modules.job.task;

import com.dg.cloud.fast.modules.sys.entity.SysDepEntity;
import com.dg.cloud.fast.modules.sys.service.SysDepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("demoTask")
public class DemoTask implements ITask {


    @Autowired
    private SysDepService sysDepService;
    @Override
    public void run(String params) {
        System.out.println("开始执行定时任务。。。。。。。。。。"+params);
        this.updateDep();
    }

    /**10 * * * * ?
     * 每60秒去修改部门的信息
     */
    public void updateDep(){
        List<SysDepEntity> list = sysDepService.list(null);
        list.stream().forEach(s->{
            s.setUpdateTime(LocalDateTime.now());
            sysDepService.updateById(s);
        });
    }
}
