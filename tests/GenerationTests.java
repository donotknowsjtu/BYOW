import core.WorldGeneration;

public class GenerationTests {
    /** @Test for 内部和外部世界生成
     * @Created_time 2025.3.6
     * @Author 张书源
     * @Descripiton 由于junit的test包无法长时间运行程序，简言之就是程序无误就关闭程序了，然而我们的测试需要观察一下跑出来的结果，同时不需要设置预期结果，因为结果是随机的，所以测试没有使用test包
     * @Assigned 王宇扬 ，等王宇扬完成OutersideGeneration()方法的编写即可对整个WorldGeneration进行测试/
     **/
        public static void main(String args[]){
            new WorldGeneration(60, 60, 12345).WorldRender();
        }
}
