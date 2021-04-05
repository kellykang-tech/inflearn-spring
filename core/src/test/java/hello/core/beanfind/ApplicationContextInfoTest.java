package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    // @Bean 이라고 달면 빈으로 등록이 된다는데, 어떻게 빈으로 등록이 되는 건지 알아보자.
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) { // 이름, 객체 저장된 걸 꺼내보자.
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName +"  "+ "object = " + bean);

        }
    }


    // 출력 했을 때 스프링이 내부에서 사용하는 빈 말고, AppConfig에 등록된 애들만 보고 싶을 때.
    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) { // 이름, 객체 저장된 걸 꺼내보자.
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // Role     ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            // Role     ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + "  " + "object = " + bean);
            }

        }
    }


}
