package eu.europa.ec.edelivery.smp.services.ui;

import eu.europa.ec.edelivery.smp.data.dao.BaseDao;
import eu.europa.ec.edelivery.smp.data.model.BaseEntity;
import eu.europa.ec.edelivery.smp.data.ui.ServiceResult;
import eu.europa.ec.edelivery.smp.logging.SMPLogger;
import eu.europa.ec.edelivery.smp.logging.SMPLoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joze Rihtarsic
 * @since 4.1
 */
abstract class UIServiceBase<E extends BaseEntity, R> {

    private static final SMPLogger LOG = SMPLoggerFactory.getLogger(UIServiceBase.class);

    private final Class<R> roClass;


    public UIServiceBase() {
        Class[] clsArg = GenericTypeResolver.resolveTypeArguments(getClass(), UIServiceBase.class);
        roClass =(Class<R>)clsArg[1];

    }

    /**
     * Method returns UI  resource object list for page.
     *
     * @param page
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */

    @Transactional
    public ServiceResult<R> getTableList(int page, int pageSize,
                                         String sortField,
                                         String sortOrder) {

        ServiceResult<R> sg = new ServiceResult<>();
        sg.setPage(page<0?0:page);
        sg.setPageSize(pageSize);
        long iCnt = getDatabaseDao().getDataListCount(null);
        sg.setCount(iCnt);

        if (iCnt > 0) {
            int iStartIndex = pageSize<0?-1:page * pageSize;
            List<E> lst = getDatabaseDao().getDataList(iStartIndex, pageSize, sortField, sortOrder, null);

            List<R>  lstRo = new ArrayList<>();
            for (E d : lst) {
                try {
                    R dro = roClass.newInstance();
                    BeanUtils.copyProperties(dro,d);
                    lstRo.add(dro);
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    LOG.error("Error occured while retrieving list for " +roClass.getName(), e );
                }


            }
            sg.getServiceEntities().addAll(lstRo);
        }
        return sg;
    }

    protected abstract BaseDao<E> getDatabaseDao();
}
