package com.dg.cloud.fast.modules.job.task;

/**
 * 定时任务接口，所有定时任务都要实现该接口
 *如果需要做定时任务只需要新建一个类实现该方法的run方法，就可以开始写定时任务
 * @author Mark sunlightcs@gmail.com
 */
public interface ITask {

    /**
     * 执行定时任务接口
     *
     * @param params   参数，多参数使用JSON数据
     */
    void run(String params);
}