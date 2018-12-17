package org.poem;

import com.google.common.collect.Sets;
import org.poem.config.annotation.ShiroOauthodIgnore;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author poem
 */
public class OAuthIgnoreUtils {

    /**
     * 获取数据
     *
     * @param classPath  类上面的RequestMapping路径
     * @param methodPath 方法上面的RequestMapping路径
     * @return
     */
    private static Set<String> getPath(String[] classPath, String[] methodPath) {
        Set<String> path = Sets.newHashSet();
        if (classPath.length > 0 && methodPath.length > 0) {
            Stream.of(classPath).forEach(cl -> {
                Stream.of(methodPath).forEach(me -> {
                    path.add(cl + me);
                });
            });
        } else if (classPath.length > 0 && methodPath.length == 0) {
            path.addAll(Arrays.asList(classPath));
        } else if (classPath.length == 0 && methodPath.length >= 0) {
            path.addAll(Arrays.asList(methodPath));
        }
        return path;
    }

    /**
     * 获取通过
     *
     * @param packageName
     * @return
     */
    public static Set<String> getOAthodPath(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RestController.class);
        classesList.addAll(reflections.getTypesAnnotatedWith(Controller.class));

        Set<String> urls = new HashSet<>();
        for (Class classes : classesList) {
            ShiroOauthodIgnore classAuthIgnore = (ShiroOauthodIgnore) classes.getAnnotation(ShiroOauthodIgnore.class);
            RequestMapping requestMapping = (RequestMapping) classes.getAnnotation(RequestMapping.class);
            if (classAuthIgnore != null) {
                continue;
            }
            String[] name = new String[0];
            if (requestMapping != null) {
                name = requestMapping.value();
                for (String s : name) {
                    urls.add(s + "/**");
                }
            }

            //得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            for (Method method : methods) {
                //得到该类下面的RequestMapping注解
                ShiroOauthodIgnore annotation = method.getAnnotation(ShiroOauthodIgnore.class);
                if (null == annotation) {
                    RequestMapping methodRequestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                    if (methodRequestMapping != null) {
                        urls.addAll(getPath(name, methodRequestMapping.value()));
                        continue;
                    }
                    GetMapping methodGetMapping = method.getDeclaredAnnotation(GetMapping.class);
                    if (methodGetMapping != null) {
                        urls.addAll(getPath(name, methodGetMapping.value()));
                        continue;
                    }
                    PostMapping methodPostMapping = method.getDeclaredAnnotation(PostMapping.class);
                    if (methodPostMapping != null) {
                        urls.addAll(getPath(name, methodPostMapping.value()));
                    }
                }
            }
        }

        return urls.stream().map(p -> p.replaceAll("//", "/")).collect(Collectors.toSet());
    }

}
