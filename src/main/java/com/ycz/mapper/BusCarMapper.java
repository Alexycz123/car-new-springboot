package com.ycz.mapper;/*
 @author ycz
 @date 2021-07-02-19:23
*/

import com.ycz.domain.BusCar;
import com.ycz.form.BusCarForm;
import com.ycz.query.BusCarQuery;
import com.ycz.vo.BusCarVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Mapper
public interface BusCarMapper extends tk.mybatis.mapper.common.Mapper<BusCarVo>, MySqlMapper<BusCarVo> {
    List<BusCarVo> selectCarList(BusCarQuery busCarQuery);

    BusCar selectCarByNum(BusCarQuery query);

    void insertCar(BusCarForm busCarForm);

    BusCarVo selectCarAll(BusCarForm busCarForm);

    void updateCar(BusCarForm busCarForm);

    void deleteCar(@Param("id") Integer id);

    Integer updateCar_isRent_version_ByID(@Param("id")Integer id, @Param("isRent") Integer isRent, @Param("version")String version);

    List<BusCarVo> selectCarList2();
}
