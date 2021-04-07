package hello.core.singleton;

public class SingletonService {     //싱글톤 패턴
    //자기 자신을 가진다. 클래스 영역
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    //누군가가 외부에서 new로 인스턴스 생성하는 것을 막음
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
    // logic() 메서드 하나를 호출하기 위해 위 코드들을 다 작성해야 함.
}
