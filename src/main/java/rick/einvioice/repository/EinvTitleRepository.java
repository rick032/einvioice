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
    @Query(value="select * from EINVTITLE where INV_DATE >= ?1 order by INV_DATE,row_num" ,nativeQuery = true)
    List<EinvTitleBean> findByInvDate(String invDate);
}
