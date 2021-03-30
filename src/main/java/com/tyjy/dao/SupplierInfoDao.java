package com.tyjy.dao;


        import com.tyjy.pojo.SupplierInfo;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Select;

        import java.util.List;

@Mapper
public interface SupplierInfoDao {
    @Select("SELECT * from supplierInfo")
    List<SupplierInfo> select();

    @Select("SELECT * from supplierInfo where supplierId=#{supplierId}")
    SupplierInfo findSupplier(int supplierId);



    @Insert("insert into supplierInfo (Name,tel) values(#{Name},#{tel})")
    int add(SupplierInfo supplierInfo);

    @Select("SELECT count(*) from supplierInfo where Name=#{Name} and tel=#{tel}")
    int selectcount(SupplierInfo supplierInfo);

    @Select("SELECT * from supplierInfo where Name=#{Name} and tel=#{tel}")
    SupplierInfo selectToOperating(SupplierInfo supplierInfo);
}
