MBG
下载地址：</br>
https://github.com/astarring/mybatis-generator-gui


- 类名大写，方法名小写。 类的属性用驼峰命名法命名。
- 类文件按照功能模块划分干净。根据不同模块划分，类不要乱放。（包括easyui的页面代码以及java代码）
- mvc职责分层，controller中只能调用service方法，不可以在controller中直接调用mapper方法去操作数据库。



项目按包名 区分前台后台功能代码：

<h1> admin-管理后台

启动访问地址http://localhost:8080/admin





<h1> api对应前台接口





1, 上传文件流程。
2，充值。
3，提款。
4，资金流水科目
5, ~~~~