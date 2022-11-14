package annotationTest;
class NesoyObject{
	@NesoyAnnotation(value = "I'm Annotation") // 새로운 value를 넣을 수 있습니다.
//	@NesoyAnnotation()
	public void annotationTest(){
		System.out.println("Hello! Nesoy");
	}
}