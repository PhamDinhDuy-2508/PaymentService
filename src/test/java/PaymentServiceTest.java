import org.demo.demopaymentservice.Controller.PaymentController;

import org.demo.demopaymentservice.Facade.Impl.PaymentFacadeImpl;
import org.demo.demopaymentservice.Facade.PaymentFacade;
import org.demo.demopaymentservice.InitScheduleTask;
import org.demo.demopaymentservice.Service.Impl.PaymentServiceImpl;
import org.demo.demopaymentservice.Service.PaymentService;
import org.demo.demopaymentservice.Type.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;



import static org.mockito.Mockito.times;

public class PaymentServiceTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private InitScheduleTask initScheduleTask;

    @Mock
    private PaymentFacade paymentFacade;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testListBills() {
        paymentService = new PaymentServiceImpl();
        initScheduleTask = new InitScheduleTask(paymentService);
        paymentFacade = new PaymentFacadeImpl(paymentService);
        paymentController = new PaymentController(paymentFacade);
        paymentController.listBills();
         Mockito.verify(paymentFacade, times(1)).listBill();
    }

//    @Test
//    void testPay() throws ExecutionException, InterruptedException {
//        List<Integer> ids = new ArrayList<>();
//        ids.add(1);
//        ids.add(2);
//        CountDownLatch countDownLatch =  new CountDownLatch(1);
//        paymentController.pay(ids);
//         Mockito.verify(paymentFacade, times(1)).pay(countDownLatch,ids);
//    }

    @Test
    void testDueDate() {
        paymentController.dueDate();
         Mockito.verify(paymentFacade, times(1)).dueDateList();
    }

    @Test
    void testSchedule() {
        int id = 1;
        String date = "2023-07-13";
        paymentController.schedule(id, date);
         Mockito.verify(paymentFacade, times(1)).schedule(id, date);
    }

    @Test
    void testListPayment() {
        paymentController.listPayment();
         Mockito.verify(paymentFacade, times(1)).listPayment();
    }

    @Test
    void testSearch() {
        String field = "PROVIDER";
        String value = "VNPT";
        paymentController.search(field, value);
         Mockito.verify(paymentFacade, times(1)).searchBill(Field.valueOf("PROVIDER"), value);
    }
}
