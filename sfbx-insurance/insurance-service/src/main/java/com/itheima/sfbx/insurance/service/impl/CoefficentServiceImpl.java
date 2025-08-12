package com.itheima.sfbx.insurance.service.impl;

import com.itheima.sfbx.framework.commons.constant.basic.SuperConstant;
import com.itheima.sfbx.insurance.pojo.Coefficent;
import com.itheima.sfbx.insurance.mapper.CoefficentMapper;
import com.itheima.sfbx.insurance.service.ICoefficentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.sfbx.insurance.constant.CoefficentCacheConstant;
import com.itheima.sfbx.insurance.dto.CoefficentVO;
import com.itheima.sfbx.insurance.enums.CoefficentEnum;
import com.itheima.sfbx.framework.commons.utils.BeanConv;
import com.itheima.sfbx.framework.commons.utils.EmptyUtil;
import com.itheima.sfbx.framework.commons.utils.ExceptionsUtil;
import com.itheima.sfbx.framework.commons.exception.ProjectException;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description：系数项服务实现类
 */
@Slf4j
@Service
public class CoefficentServiceImpl extends ServiceImpl<CoefficentMapper, Coefficent> implements ICoefficentService {


    /***
    * @description 系数项多条件组合
    * @param coefficentVO 系数项
    * @return QueryWrapper查询条件
    */
    private QueryWrapper<Coefficent> queryWrapper(CoefficentVO coefficentVO){
        QueryWrapper<Coefficent> queryWrapper = new QueryWrapper<>();
        //系数键名称查询
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getCoefficentKeyName())) {
            queryWrapper.lambda().eq(Coefficent::getCoefficentKeyName,coefficentVO.getCoefficentKeyName());
        }
        //系数键查询
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getCoefficentKey())) {
            queryWrapper.lambda().eq(Coefficent::getCoefficentKey,coefficentVO.getCoefficentKey());
        }
        //系数项类型：0、选项系数1、范围类型
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getCoefficentType())) {
            queryWrapper.lambda().eq(Coefficent::getCoefficentType,coefficentVO.getCoefficentType());
        }
        //排序查询
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getSortNo())) {
            queryWrapper.lambda().eq(Coefficent::getSortNo,coefficentVO.getSortNo());
        }
        //备注查询
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getRemake())) {
            queryWrapper.lambda().eq(Coefficent::getRemake,coefficentVO.getRemake());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getDataState())) {
            queryWrapper.lambda().eq(Coefficent::getDataState,coefficentVO.getDataState());
        }
        //状态查询
        if (!EmptyUtil.isNullOrEmpty(coefficentVO.getCheckedIds())) {
            queryWrapper.lambda().in(Coefficent::getCoefficentKey,coefficentVO.getCheckedIds());
        }
        //按创建时间降序
        queryWrapper.lambda().orderByDesc(Coefficent::getCreateTime);
        return queryWrapper;
    }

    @Override
    @Cacheable(value = CoefficentCacheConstant.PAGE,key ="#pageNum+'-'+#pageSize+'-'+#coefficentVO.hashCode()")
    public Page<CoefficentVO> findPage(CoefficentVO coefficentVO, int pageNum, int pageSize) {
        try {
            //构建分页对象
            Page<Coefficent> CoefficentPage = new Page<>(pageNum,pageSize);
            //构建查询条件
            QueryWrapper<Coefficent> queryWrapper = queryWrapper(coefficentVO);
            //执行分页查询
            Page<CoefficentVO> coefficentVOPage = BeanConv.toPage(
                page(CoefficentPage, queryWrapper), CoefficentVO.class);
            //返回结果
            return coefficentVOPage;
        }catch (Exception e){
            log.error("系数项分页查询异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.PAGE_FAIL);
        }
    }

    @Override
    @Cacheable(value = CoefficentCacheConstant.BASIC,key ="#coefficentId")
    public CoefficentVO findById(String coefficentId) {
        try {
            //执行查询
            return BeanConv.toBean(getById(coefficentId),CoefficentVO.class);
        }catch (Exception e){
            log.error("系数项单条查询异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.FIND_ONE_FAIL);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = CoefficentCacheConstant.PAGE,allEntries = true),
        @CacheEvict(value = CoefficentCacheConstant.LIST,allEntries = true)},
        put={@CachePut(value =CoefficentCacheConstant.BASIC,key = "#result.id")})
    public CoefficentVO save(CoefficentVO coefficentVO) {
        try {
            //转换CoefficentVO为Coefficent
            Coefficent coefficent = BeanConv.toBean(coefficentVO, Coefficent.class);
            boolean flag = save(coefficent);
            if (!flag){
                throw new RuntimeException("保存系数项失败");
            }
            //转换返回对象CoefficentVO
            CoefficentVO coefficentVOHandler = BeanConv.toBean(coefficent, CoefficentVO.class);
            return coefficentVOHandler;
        }catch (Exception e){
            log.error("保存系数项异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.SAVE_FAIL);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = CoefficentCacheConstant.PAGE,allEntries = true),
        @CacheEvict(value = CoefficentCacheConstant.LIST,allEntries = true),
        @CacheEvict(value = CoefficentCacheConstant.BASIC,key = "#coefficentVO.id")})
    public Boolean update(CoefficentVO coefficentVO) {
        try {
            //转换CoefficentVO为Coefficent
            Coefficent coefficent = BeanConv.toBean(coefficentVO, Coefficent.class);
            boolean flag = updateById(coefficent);
            if (!flag){
                throw new RuntimeException("修改系数项失败");
            }
            return flag;
        }catch (Exception e){
            log.error("修改系数项异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.UPDATE_FAIL);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = CoefficentCacheConstant.PAGE,allEntries = true),
        @CacheEvict(value = CoefficentCacheConstant.LIST,allEntries = true),
        @CacheEvict(value = CoefficentCacheConstant.BASIC,allEntries = true)})
    public Boolean delete(String[] checkedIds) {
        try {
            List<Long> idsLong = Arrays.asList(checkedIds)
                .stream().map(Long::new).collect(Collectors.toList());
            boolean flag = removeByIds(idsLong);
            if (!flag){
                throw new RuntimeException("删除系数项失败");
            }
            return flag;
        }catch (Exception e){
            log.error("删除系数项异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.DEL_FAIL);
        }
    }

    @Override
    @Cacheable(value = CoefficentCacheConstant.LIST,key ="#coefficentVO.hashCode()")
    public List<CoefficentVO> findList(CoefficentVO coefficentVO) {
        try {
            //构建查询条件
            QueryWrapper<Coefficent> queryWrapper = queryWrapper(coefficentVO);
            //执行列表查询
            List<CoefficentVO> coefficentVOs = BeanConv.toBeanList(list(queryWrapper),CoefficentVO.class);
            return coefficentVOs;
        }catch (Exception e){
            log.error("系数项列表查询异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.LIST_FAIL);
        }
    }

    @Override
    @Cacheable(value = CoefficentCacheConstant.BASIC,key ="#coefficentKey")
    public CoefficentVO findByCoefficentKey(String coefficentKey) {
        try {
            //构建查询条件
            QueryWrapper<Coefficent> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Coefficent::getCoefficentKey,coefficentKey)
                    .eq(Coefficent::getDataState, SuperConstant.DATA_STATE_0);
            //执行列表查询
            return  BeanConv.toBean(getOne(queryWrapper),CoefficentVO.class);
        }catch (Exception e){
            log.error("系数项查询异常：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(CoefficentEnum.FIND_ONE_FAIL);
        }
    }
}
