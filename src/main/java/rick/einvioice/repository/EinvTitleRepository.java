package rick.einvioice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rick.einvioice.model.EinvTitleBean;

import java.util.List;

@Repository
public interface EinvTitleRepository extends CrudRepository<EinvTitleBean, Long> {
    List<EinvTitleBean> findAll();
    @Query(value="select * from EINVTITLE where INV_NUM = ?1" ,nativeQuery = true)
    EinvTitleBean findByInvNum(String invNum);
    @Query(value="select * from EINVTITLE where INV_DATE BETWEEN ?1 and ?2  order by INV_DATE,row_num" ,nativeQuery = true)
    List<EinvTitleBean> findByInvDate(String startDate, String endDate);
    @Query(value="select * from EINVTITLE T LEFT JOIN EINVDETIAL D ON T.ID=D.TITLE_ID where T.INV_DATE BETWEEN ?1 and ?2 AND D.description LIKE ?3 order by INV_DATE,T.row_num,D.ROW_NUM" ,nativeQuery = true)
    List<EinvTitleBean> findByDesc(String startDate, String endDate,String desc);
    @Query(value="select * from EINVTITLE where INV_DATE BETWEEN ?1 and ?2 AND SELLER_NAME LIKE ?3 order by INV_DATE,row_num" ,nativeQuery = true)
    List<EinvTitleBean> findBySellerName(String startDate, String endDate,String sellerName);
}
