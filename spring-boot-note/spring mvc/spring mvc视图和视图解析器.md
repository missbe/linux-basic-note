视图是渲染数据模型展示给用户的组件，spring mvc中分为逻辑视图和非逻辑视图。逻辑视图需要视图解析器(ViewResolver)进行进一步的定位，定位后找到视图进行渲染展示给用户。非逻辑视图直接对数据进行渲染(MappingJackson2JsonView)。

> 视图设计->除了json和jsp视图还有pdf、excel等都会实现spring mvc定义的视图接口View，源码如下：
>
> ```java
> public interface View {
>     /*响应状态属性*/
>     String RESPONSE_STATUS_ATTRIBUTE = View.class.getName() + ".responseStatus";
>     /*路径变量*/
>     String PATH_VARIABLES = View.class.getName() + ".pathVariables";
>     /*选择内容类型*/
>     String SELECTED_CONTENT_TYPE = View.class.getName() + ".selectedContentType";
>     /*响应类型*/
>     @Nullable
>     default String getContentType() {
>         return null;
>     }
>     /*渲染方法*/
>     void render(@Nullable Map<String, ?> var1, HttpServletRequest var2, HttpServletResponse var3) throws Exception;
> }
> ```
>
> -->spring mvc常用视图模型
>
> ![spring mvc常用视图模型](E:\Typora\study-basic-note\spring-boot-note\img\springmvc常用视图关系模型.png)

视图实例-导出pdf文件

><!--pom.xml中加入pdf的依赖-->
>
>```xml
>		<dependency>
>			<groupId>org.xhtmlrenderer</groupId>
>			<artifactId>core-renderer</artifactId>
>			<version>R8</version>
>		</dependency>
>		<dependency>
>			<groupId>com.itextpdf</groupId>
>			<artifactId>itextpdf</artifactId>
>			<version>5.5.12</version>
>		</dependency>
>```
>
><!--定义PDF导出接口-->
>
>```java
>public interface PdfExportService {
>	public void make(Map<String, Object> model, Document document,
>	        PdfWriter writer, HttpServletRequest request,
>	        HttpServletResponse response);
>}
>```
>
><!--PDF导出视图类-->
>
>```java
>public class PdfView extends AbstractPdfView {
>    // 导出服务接口
>    private PdfExportService pdfExportService = null;
>    
>    // 创建对象的时候载入导出服务接口
>    public PdfView(PdfExportService pdfExportService) {
>        this.pdfExportService = pdfExportService;
>    }
>    
>    // 调用接口实现
>    @Override
>    protected void buildPdfDocument(Map<String, Object> model, Document document, 
>        PdfWriter writer,HttpServletRequest request, 
>        HttpServletResponse response) throws Exception {
>        // 调用导出服务接口类
>        pdfExportService.make(model, document, writer, request, response);
>    }
>}
>```
>
><!--在用户控制器中导出数据-->
>
>```java
>@Controller
>@RequestMapping("/user")
>public class UserController {
>    // 导出接口
>	@GetMapping("/export/pdf")
>	public ModelAndView exportPdf(String userName, String note) {
>		// 查询用户信息列表
>		List<User> userList = userService.findUsers(userName, note);
>		// 定义PDF视图
>		View view = new PdfView(exportService());
>		ModelAndView mv = new ModelAndView();
>		// 设置视图
>		mv.setView(view);
>		// 加入数据模型
>		mv.addObject("userList", userList);
>		return mv;
>	}
>
>	// 导出PDF自定义
>	@SuppressWarnings("unchecked")
>	private PdfExportService exportService() {
>		// 使用Lambda表达式定义自定义导出
>		return (model, document, writer, request, response) -> {
>			try {
>				// A4纸张
>				document.setPageSize(PageSize.A4);
>				// 标题
>				document.addTitle("用户信息");
>				// 换行
>				document.add(new Chunk("\n"));
>				// 表格，3列
>				PdfPTable table = new PdfPTable(3);
>				// 单元格
>				PdfPCell cell = null;
>				// 字体，定义为蓝色加粗
>				Font f8 = new Font();
>				f8.setColor(Color.BLUE);
>				f8.setStyle(Font.BOLD);
>				// 标题
>				cell = new PdfPCell(new Paragraph("id", f8));
>				// 居中对齐
>				cell.setHorizontalAlignment(1);
>				// 将单元格加入表格
>				table.addCell(cell);
>				cell = new PdfPCell(new Paragraph("user_name", f8));
>				// 居中对齐
>				cell.setHorizontalAlignment(1);
>				table.addCell(cell);
>				cell = new PdfPCell(new Paragraph("note", f8));
>				cell.setHorizontalAlignment(1);
>				table.addCell(cell);
>				// 获取数据模型中的用户列表
>				List<User> userList = (List<User>) model.get("userList");
>				for (User user : userList) {
>					document.add(new Chunk("\n"));
>					cell = new PdfPCell(new Paragraph(user.getId() + ""));
>					table.addCell(cell);
>					cell = new PdfPCell(new Paragraph(user.getUserName()));
>					table.addCell(cell);
>					String note = user.getNote() == null ? "" : user.getNote();
>					cell = new PdfPCell(new Paragraph(note));
>					table.addCell(cell);
>				}
>				// 在文档中加入表格
>				document.add(table);
>			} catch (DocumentException e) {
>				e.printStackTrace();
>			}
>		};
>	}
>}
>```
>
>

