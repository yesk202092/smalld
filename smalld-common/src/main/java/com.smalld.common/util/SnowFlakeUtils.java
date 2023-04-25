package com.smalld.common.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yesk
 */

@Component
@Order(3)
public class SnowFlakeUtils {
    /*
        起始时间时间戳：这个时间为第一次运行时的时间，这里设置为2021/11/23/19/17
        可以在未来的69年内稳定运行
     */
    private final static long START_STMP = 1637666189914L;


    private final static long SEQUENCE_BIT = 12;//序列号占用12bit
    private final static long MACHINE_BIT = 5;//机器号占用5bit
    private final static long MACHINE_HOUSE_BIT = 5;//机房号占用5bit
    /*
        -1的源码   10000001
        -1的反码   11111110
        -1的补码   11111111
        -1左移12位= 1111 1111 0000 0000 0000
        -1       = 1111 1111 1111 1111 1111
        异或运算  = 0000 0000 1111 1111 1111=4095
        因此MAX_SEQUENCE的值为4095
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    //同理 MAX_MACHINE为31
    private final static long MAX_MACHINE = -1L ^ (-1L << MACHINE_BIT);
    //同理 MAX_MACHINE_HOUSE值为31
    private final static long MAX_MACHINE_HOUSE = -1L ^ (-1L << MACHINE_HOUSE_BIT);
    //机器ID
    private long machineID;
    //机房ID
    private long machineHouseID;
    private long lastTime = 0;//上一次生成ID的时间戳
    private long sequence = 0;//序列号，默认为0


    public long getMachineID() {
        return machineID;
    }

    public void setMachineID(long machineID) {
        this.machineID = machineID;
    }

    public long getMachineHouseID() {
        return machineHouseID;
    }

    public void setMachineHouseID(long machineHouseID) {
        this.machineHouseID = machineHouseID;
    }


    /***
     *产生下一个ID
     * 用long型来表示我们生成的64位ID，
     * @return
     */

    public synchronized long nextId() {
        if (machineHouseID > MAX_MACHINE_HOUSE || machineID > MAX_MACHINE) {
            throw new RuntimeException("机房ID或机器ID超出最大值");
        }
        //获取当前时间戳
        long currentTime = System.currentTimeMillis();
        //如果当前时间小于上一次生成ID的时间，抛出异常
        if (currentTime < lastTime) {
            throw new RuntimeException("当前时间为异常值，请勿回拨时间！");
        }
        //如果当前时间等于上一次生成ID时间，说明是在同一毫秒中生成，那么序列号加一
        else if (currentTime == lastTime) {
            /*
                MAX_SEQUENCE: 0000 1111 1111 1111
                            &
                        4096: 0001 0000 0000 0000
                           = 0
                 当sequence小于4095时， (sequence+1)&MAX_SEQUENCE=sequence+1
                 当sequence等于4095时，(sequence+1)&MAX_SEQUENCE=0
             */
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                //获取下一个毫秒值
                currentTime = getNextMill();
            }

        } else {
            //毫秒值不同，sequence初始为0
            sequence = 0L;
        }
        //更新最近一次生成时间的毫秒值
        lastTime = currentTime;
        return (currentTime - START_STMP) << 22//左移22位 空出机房ID5位+机器ID5位+序列号12位
                | machineID << 12//左移12位 空出序列号12位
                | machineHouseID << 17//左移17位 空出机器ID5位+序列号12位
                | sequence;//序列号部分
    }

    /**
     * 获取下一个毫秒值
     *
     * @return
     */
    private long getNextMill() {
        long mill = System.currentTimeMillis();
        //如果当前时间等于上一次的时间则一直自旋
        while (mill == lastTime) {
            mill = System.currentTimeMillis();
        }
        return mill;

    }

    public String getSnowNextId() {
        //默认18位
        //默认时间为年
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date()) + nextId();
    }

    public String getSnowNextIdByDate(String pattern) {
        //默认18位
        //默认时间为年
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date()) + nextId();
    }

    public String getSnowNextIdByDateAndPrefix(String pattern, String prefix) {
        //默认18位
        return prefix + getSnowNextIdByDate(pattern);
    }

    public String getSnowNextIdByDateAndPrefix(String prefix) {
        //默认18位
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return prefix + sdf.format(new Date()) + getSnowNextId();
    }

    public String getSnowNextIdByPrefix(String prefix) {
        //默认18位
        return prefix + nextId();
    }

    public String getSnowNextIdByPrefix(String prefix, Integer size) {
        //默认18位
        String id = String.valueOf(nextId());
        if (StringUtils.isEmpty(prefix)) {
            prefix = "";
        }
        if (null == size) {
            size = 14;
        }
        return prefix + id.substring((18 - size));
    }

    public String getSnowNextIdBySuffix(String suffix) {
        //默认18位
        return nextId() + suffix;
    }

}
