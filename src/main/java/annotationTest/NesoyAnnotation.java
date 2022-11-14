package annotationTest;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Inherited // 상속
@Documented // 문서에 정보가 표현
@Retention(RetentionPolicy.RUNTIME) // 컴파일 이후에도 JVM에 의해서 참조가 가능합니다
//@Retention(RetentionPolicy.CLASS)   // Compiler가 클래스를 참조할 때까지 유효합니다
//@Retention(RetentionPolicy.SOURCE)  // 컴파일 이후 이후 사라집니다
@Target({
		ElementType.PACKAGE, // 패키지 선언시
		ElementType.TYPE, // 타입 선언시
		ElementType.CONSTRUCTOR, // 생성자 선언시
		ElementType.FIELD, // 멤버 변수 선언시
		ElementType.METHOD, // 메소드 선언시
		ElementType.ANNOTATION_TYPE, // 어노테이션 타입 선언시
		ElementType.LOCAL_VARIABLE, // 지역 변수 선언시
		ElementType.PARAMETER, // 매개 변수 선언시
		ElementType.TYPE_PARAMETER, // 매개 변수 타입 선언시
		ElementType.TYPE_USE // 타입 사용시
})
public @interface NesoyAnnotation{
	 enum 타입을 선언할 수 있습니다. 
	public enum Quality {
		BAD, GOOD, VERYGOOD
	}

	 String은 기본 자료형은 아니지만 사용 가능합니다. 
	String value() default "NesoyAnnotation : Default String Value";

	 배열 형태로도 사용할 수 있습니다. 
	int[] values();

	 enum 형태를 사용하는 방법입니다. 
	Quality quality() default Quality.GOOD;
}
*/
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME) // 컴파일 이후에도 JVM에 의해서 참조가 가능합니다
@Target({ ElementType.METHOD, // 메소드 선언시
})
public @interface NesoyAnnotation {
	String value() default "NesoyAnnotation : Default String Value"; // 기본 값으로 확인할 수 있습니다.
}