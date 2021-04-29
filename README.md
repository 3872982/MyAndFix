### 手写AndFix

基于android 6.0版本

思路：AndFix是基于Native层对bug进行修复的，当我们APP启动后，会在方法区（静态存储区）开辟一个int变量，指向我们的.class文件
并且将class中的方法加载到方法区中的方法表中，这个方法表是个列表，列表中的每一项都是一个ArtMethod结构体，换句话说，我们的每个方法
都被封装成ArtMethod结构体保存在方法区中，我们要做的就是在native层将错误的ArtMethod结构体，指向我们修复包中加载的正确的ArtMethod结构体方法。

#### AndFix优缺点
优点：不需要重启APP，直接修复
缺点：修复依赖ArtMethod，不同厂商不同版本的手机ArtMethod都不一样，需要适配，比较麻烦。

#### Sophix
AndFix进阶版：主要思路动态计算，根据不同手机动态计算ArtMethod结构体所需要的地址，然后在填充我们自己定义的ArtMethod头文件后，缺少的部分
通过动态填充的方式填充起来，防止地址的便宜发生....由于Sophix未开源..所以猜的....