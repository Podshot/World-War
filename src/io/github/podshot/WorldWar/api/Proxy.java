package io.github.podshot.WorldWar.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Proxy {
	
	public enum ProxyType {
		UUID_Player, 
		Player_UUID
	}

	ProxyType value();
}
