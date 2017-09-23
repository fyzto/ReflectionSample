import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class Inspector {
	public Inspector(Class obj){
		System.out.println("Initializing inspector... \n Object: " + obj.getPackage());
		this.cname = obj;
		this.food = true; 

	}
	public void showConstructors(){
		Constructor[] ctors = cname.getConstructors();
		constructorPrinter(ctors);

	}
	public void showDeclaredConstructors(){
		Constructor[] ctors = cname.getDeclaredConstructors();
		constructorPrinter(ctors);
	}
	public void showMethods(){
		if (food) {
			Method[] methods = cname.getMethods();
			methodPrinter(methods);
		}
	}
	public void showDeclaredMethods(){
		if(food) {
			Method[] methods = cname.getDeclaredMethods();
			methodPrinter(methods);
		}
	}
	public void showFields(){
		if(food) {
			Field[] fields = cname.getFields();
			fieldPrinter(fields);
		}
	}
	public void showDeclaredFields(){
		if(food){
			Field[] fields = cname.getDeclaredFields();
			fieldPrinter(fields);
		}
	}
	private void constructorPrinter(Constructor[] ctors){
		Class[] paramT;
		String ctName;
		if(ctors.length == 0 ){
			System.out.println("No public constructors in this class");
			return ;
		}

		for(Constructor ctor: ctors){
			paramT = ctor.getParameterTypes();
			System.out.print(ctor.getName()+"(");
			if(paramT.length > 0){
				System.out.print(this.parseParams(paramT));	
			}
			System.out.println(")");
		}
	}
	private void fieldPrinter(Field[] fields){
		for(Field field: fields){
			System.out.println("Field: " + field.getType().getName() + " " + field.getName());
		}
	}
	private void methodPrinter(Method[] methods){
		Class returnType;
		Class[] paramTypes;
		String rtType;
		for (Method method : methods) {
			returnType = method.getReturnType();
			paramTypes = method.getParameterTypes();
			rtType = returnType.isArray() ? returnType.getComponentType().getName() + "[]" : returnType.getName(); 
			System.out.print(method.getName()+"(");
			if(paramTypes.length > 0) {
				System.out.print(this.parseParams(paramTypes));
			}
			System.out.println(") -> " + rtType);
		}
	}
	private String parseParams(Class[] params){
		int plength = params.length;
		int counter = 0;
		String ctName;
		StringBuilder builder = new StringBuilder();
		for(Class param: params){
			counter += 1;
			ctName =
			param.isArray() ? param.getComponentType().getName() + "[]" : param.getName();
			builder.append(ctName + " p" + counter);
			if(counter != plength) {
				builder.append(", ");
			}
		}
		return builder.toString();
	}
	private boolean food = false;
	private Class cname;

}

public class Main {
	public static void main(String[] args){
		if(args.length < 3){ /* In my system the first param is at args[2]  */ 
			return ;
		}
		String cname = args[2];
		int order = 0;

		if (args.length > 3) {
			order = Integer.parseInt(args[3]);
		}

		System.out.println("First param: "+ cname);
		Class t;
		try {
			t = Class.forName(cname);
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		Inspector i = new Inspector(t);
		
		switch(order){
			case 1:
				i.showMethods();
				break;
			case 2:
				i.showDeclaredMethods();
				break;
			case 3:
				i.showFields();
				break;
			case 4: i.showDeclaredFields();
				break;
			default:
				System.out.println("1 for methods\n2 for declaredMethods\n3 for fields\n4 for declaredFields");
				System.out.println("Public constructors");
				i.showConstructors();
				System.out.println("Declared constructors");
				i.showDeclaredConstructors();
				break;
		}
	}
}
