package com.itheima.sfbx.framework.commons.utils;

import com.google.common.collect.Lists;
import com.itheima.sfbx.framework.commons.dto.analysis.TimeDTO;
import com.itheima.sfbx.framework.commons.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * YestordayUtils
 *
 * @author: wgl
 * @describe: TODO
 * @date: 2022/12/28 10:10
 */
@Slf4j
public class TimeHandlerUtils {

    /**
     * 获取昨日时间段
     *
     * @return
     */
    public static TimeDTO getYesterdayTime() {
        try {
            TimeDTO timeDTO = new TimeDTO();
            LocalDateTime now = LocalDateTime.now().plusDays(-1L);
            LocalDateTime maxTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
            LocalDateTime minTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
            //设置开始时间
            timeDTO.setBeginTime(minTime);
            //设置结束时间
            timeDTO.setEndTime(maxTime);
            //封装开始时间字符串
            timeDTO.setBegin(timeDTO.getTimeFormatter().format(minTime));
            //封装结束时间字符串
            timeDTO.setEnd(timeDTO.getTimeFormatter().format(maxTime));
            timeDTO.setTargetDate(LocalDate.now());
            return timeDTO;
        } catch (Exception e) {
            log.error("日期计算异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("昨日日期数据获取失败");
        }
    }

    /**
     * 获取昨日起始到结束时间段
     *
     * @return
     */
    public static TimeDTO getYesterdayTime(String reportTime) {
        try {
            reportTime = reportTime+"T00:00:00";
            TimeDTO timeDTO = new TimeDTO();
            LocalDateTime targetDateTime = LocalDateTime.parse(reportTime).plusDays(-1L);
            LocalDateTime maxTime = LocalDateTime.of(targetDateTime.toLocalDate(), LocalTime.MAX);
            LocalDateTime minTime = LocalDateTime.of(targetDateTime.toLocalDate(), LocalTime.MIN);
            //设置开始时间
            timeDTO.setBeginTime(minTime);
            //设置结束时间
            timeDTO.setEndTime(maxTime);
            //封装开始时间字符串
            timeDTO.setBegin(timeDTO.getTimeFormatter().format(minTime));
            //封装结束时间字符串
            timeDTO.setEnd(timeDTO.getTimeFormatter().format(maxTime));
            timeDTO.setTargetDate(targetDateTime.toLocalDate());
            return timeDTO;
        } catch (Exception e) {
            log.error("日期计算异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("昨日日期数据获取失败");
        }
    }

    /**
     * 获取今天起始到结束时间段
     *
     * @return
     */
    public static TimeDTO getTodayTime(String reportTime) {
        try {
            reportTime = reportTime+"T00:00:00";
            TimeDTO timeDTO = new TimeDTO();
            LocalDateTime targetDateTime = LocalDateTime.parse(reportTime);
            LocalDateTime maxTime = LocalDateTime.of(targetDateTime.toLocalDate(), LocalTime.MAX);
            LocalDateTime minTime = LocalDateTime.of(targetDateTime.toLocalDate(), LocalTime.MIN);
            //设置开始时间
            timeDTO.setBeginTime(minTime);
            //设置结束时间
            timeDTO.setEndTime(maxTime);
            //封装开始时间字符串
            timeDTO.setBegin(timeDTO.getTimeFormatter().format(minTime));
            //封装结束时间字符串
            timeDTO.setEnd(timeDTO.getTimeFormatter().format(maxTime));
            timeDTO.setTargetDate(targetDateTime.toLocalDate());
            return timeDTO;
        } catch (Exception e) {
            log.error("日期计算异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("昨日日期数据获取失败");
        }
    }


    /**
     * 获取昨日起始到结束24个时间段
     *
     * @return
     */
    public static List<TimeDTO> getYesterday24Time(String reportTime) {
        try {
            reportTime = reportTime+"T00:00:00";
            List<TimeDTO> timeDTOs = Lists.newArrayList();
            for (int i = 0; i <= 23; i++) {
                TimeDTO timeDTO = new TimeDTO();
                LocalDateTime targetDateTime = LocalDateTime.parse(reportTime).plusDays(-1).plusHours(i);
                LocalDateTime minTime = targetDateTime.withMinute(0).withSecond(0).withNano(0);
                LocalDateTime maxTime = minTime.plusHours(1).minusNanos(1);
                //设置开始时间
                timeDTO.setBeginTime(minTime);
                //设置结束时间
                timeDTO.setEndTime(maxTime);
                //封装开始时间字符串
                timeDTO.setBegin(timeDTO.getTimeFormatter().format(minTime));
                //封装结束时间字符串
                timeDTO.setEnd(timeDTO.getTimeFormatter().format(maxTime));
                timeDTO.setTargetDateTime(targetDateTime);
                timeDTOs.add(timeDTO);
            }

            return timeDTOs;
        } catch (Exception e) {
            log.error("日期计算异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("昨日日期数据获取失败");
        }
    }

    /**
     * 获取今天起始到结束24个时间段
     *
     * @return
     */
    public static List<TimeDTO> getToday24Time(String reportTime) {
        try {
            reportTime = reportTime+"T00:00:00";
            List<TimeDTO> timeDTOs = Lists.newArrayList();
            for (int i = 0; i <= 23; i++) {
                TimeDTO timeDTO = new TimeDTO();
                LocalDateTime targetDateTime = LocalDateTime.parse(reportTime).plusHours(i);
                LocalDateTime minTime = targetDateTime.withMinute(0).withSecond(0).withNano(0);
                LocalDateTime maxTime = minTime.plusHours(1).minusNanos(1);
                //设置开始时间
                timeDTO.setBeginTime(minTime);
                //设置结束时间
                timeDTO.setEndTime(maxTime);
                //封装开始时间字符串
                timeDTO.setBegin(timeDTO.getTimeFormatter().format(minTime));
                //封装结束时间字符串
                timeDTO.setEnd(timeDTO.getTimeFormatter().format(maxTime));
                timeDTO.setTargetDateTime(targetDateTime);
                timeDTOs.add(timeDTO);
            }

            return timeDTOs;
        } catch (Exception e) {
            log.error("日期计算异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("昨日日期数据获取失败");
        }
    }

    /**
     * 根据时间单位获取其中的每一个时间节点
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @param timeUnit 时间单位
     * @return List<String> 包含时间节点的列表
     */
    public static List<String> getGraduallys(String beginTime, String endTime, TimeUnit timeUnit) {
        List<String> timeNodes = new ArrayList<>();
        SimpleDateFormat dateFormat = getDateFormat(beginTime);
        try {
            Date start = dateFormat.parse(beginTime);
            Date end = dateFormat.parse(endTime);
            long duration = end.getTime() - start.getTime();
            switch (timeUnit) {
                case DAYS:
                    long days = TimeUnit.MILLISECONDS.toDays(duration);
                    for (long i = 0; i <= days; i++) {
                        Date node = new Date(start.getTime() + TimeUnit.DAYS.toMillis(i));
                        timeNodes.add(dateFormat.format(node));
                    }
                    break;
                case HOURS:
                    long hours = TimeUnit.MILLISECONDS.toHours(duration);
                    for (long i = 0; i <= hours; i++) {
                        Date node = new Date(start.getTime() + TimeUnit.HOURS.toMillis(i));
                        timeNodes.add(dateFormat.format(node));
                    }
                    break;
                case MINUTES:
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                    for (long i = 0; i <= minutes; i++) {
                        Date node = new Date(start.getTime() + TimeUnit.MINUTES.toMillis(i));
                        timeNodes.add(dateFormat.format(node));
                    }
                    break;
                case SECONDS:
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
                    for (long i = 0; i <= seconds; i++) {
                        Date node = new Date(start.getTime() + TimeUnit.SECONDS.toMillis(i));
                        timeNodes.add(dateFormat.format(node));
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported time unit: " + timeUnit);
            }
        } catch (Exception e) {
            log.info("时间处理失败！");
        }
        return timeNodes;
    }

    /**
     * 根据传入的字符串内容判断对应的时间格式化方式
     * @param inputDate
     * @return
     */
    private static SimpleDateFormat getDateFormat(String inputDate) {
        // 正则表达式匹配日期格式
        String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(inputDate).matches()) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    }

    /**
     * 当前时间是否为早上00：00到06：00
     * @return
     */
    public static boolean isTimeInRange() {
        // 获取当前时间
        LocalTime currentTime = LocalTime.now();
        // 设置起始时间和结束时间
        LocalTime startTime = LocalTime.of(0, 0);  // 00:00
        LocalTime endTime = LocalTime.of(6, 0);    // 06:00
        return !currentTime.isBefore(startTime) && currentTime.isBefore(endTime);
    }
}
