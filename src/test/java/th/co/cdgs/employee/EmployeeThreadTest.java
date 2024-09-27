package th.co.cdgs.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class EmployeeThreadTest {

        @Test
        void createEmployeeThread() {
                int loop = 10;
                ExecutorService executor = Executors.newFixedThreadPool(10); // สร้าง Thread Pool
                List<Callable<String>> callables = new ArrayList<>();
                for (int i = 0; i < loop; i++) {
                        callables.add(new CreateEmployeeThread("/employee"));
                }
                try {
                        // รอให้ task ทั้งหมดเสร็จ
                        List<Future<String>> futures = executor.invokeAll(callables);
                        // ตรวจสอบผลลัพธ์หลังจากทุก task ทำงานเสร็จ
                        List<Employee> employees = new ArrayList<>();
                        for (Future<String> future : futures) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                Employee employee = objectMapper.readValue(future.get(),
                                                Employee.class);
                                                System.out.println(employee);
                                employees.add(employee);
                        }
                        // เรียงลำดับ
                        employees.sort(Comparator.comparingInt(Employee::getSeqNo));
                        // assert seqNo ต้องเรียงลำดับ
                        IntStream.range(0, employees.size())
                        .forEach(i -> assertEquals(i+1, employees.get(i).getSeqNo()));
                } catch (Exception e) {
                        e.printStackTrace();
                }
                executor.shutdown(); // ปิด ExecutorService
        }

}
