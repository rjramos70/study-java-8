package br.com.java8.study.entities.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {
	
	@Retention( RetentionPolicy.RUNTIME )
	@Target( { ElementType.TYPE_USE, ElementType.TYPE_PARAMETER } )
	public @interface NonEmpty{
		
	}
	
	public static class Holder<@NonEmpty T> extends @NonEmpty Object {
		public void method() throws @NonEmpty Exception {
			
		}
	}
	

}
