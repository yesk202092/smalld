package ${groupPath}.common.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis工具类
 *
 * @author yesk
 * @date 2021/9/17  14:10
 */
@Slf4j
@Service
public class RedisUtil {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean putValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean putValueByTime(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                putValue(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean putValueByTime(String key, Object value, long time,TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                putValue(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object getObject(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T getObject(String key, Class<T> beanClass) {
        if (key == null) {
            return null;
        }
        Object obj = redisTemplate.opsForValue().get(key);
        if (null == obj) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(obj), beanClass);
    }

    public String getStr(String key) {
        if (key == null) {
            return null;
        }
        Object obj = redisTemplate.opsForValue().get(key);
        if (null == obj) {
            return null;
        }
        return obj.toString();
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time,
                        TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void removeKey(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    /**
     * HashMap
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean putHashMap(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashMap 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean putHashMapByTime(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> getHashMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> getSetObj(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long put(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> getListByStAndEnd(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key获取分页对象
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        Object o = redisTemplate.opsForList().leftPop(key);
        if (o == null) {
            return null;
        }
        JSONArray array = JSONUtil.parseArray(o);
        List<T> result = array.stream().map(e -> JSONUtil.toBean(JSONUtil.toJsonStr(e), clazz)).collect(Collectors.toList());
        return result;
    }

    /**
     * 根据key获取分页对象
     */
    public <T> List<T> getListForPage(String key, Class<T> clazz, Long page) {
        Object o = redisTemplate.opsForList().index(key, (page - 1));
        if (o == null) {
            return null;
        }
        JSONArray array = JSONUtil.parseArray(o);
        List<T> result = array.stream().map(e -> JSONUtil.toBean(JSONUtil.toJsonStr(e), clazz)).collect(Collectors.toList());
        return result;
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long listGetSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean putList(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean putListByTime(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long listRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * @param key redis key, 唯一键
     * @return
     * @desc 加锁 true已锁  false未锁 手动中断锁 别忘了调releaseLock
     */
    public boolean lock(String key, String value) {
        // 对应setnx命令
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            //可以成功设置,也就是key不存在
            return true;
        }
        return false;
    }

    /**
     * @param key   redis key, 唯一键
     * @param value redis value, 这里是时间戳
     * @return
     * @desc 加锁 true已锁  false未锁
     */
    public boolean lockByTime(String key, String value, Long seconds) {
        // 对应setnx命令
        if (redisTemplate.opsForValue().setIfAbsent(key, value, seconds, TimeUnit.SECONDS)) {
            //可以成功设置,也就是key不存在
            return true;
        }
        // 判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        // 如果锁过期
        // currentValue 不为空且小于当前时间
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // 获取上一个锁的时间value
            // 对应getset，如果key存在返回当前key的值，并重新设置新的值
            // redis是单线程处理，即使并发存在，这里的getAndSet也是单个执行
            // 所以，加上下面的 !StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)
            // 就能轻松解决并发问题
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param key   redis key, 唯一键
     * @param value redis value, 这里是时间戳
     * @return
     * @desc 加锁 true已锁  false未锁 自旋锁
     */
    public boolean lockAndWait(String key, String value, Long seconds) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value, seconds, TimeUnit.SECONDS)) {
            //可以成功设置,也就是key不存在
            return true;
        } else {
            // 自旋获取锁
            // 休眠1000ms继续获取锁
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //尝试获取锁
            lockAndWait(key, value, seconds);
        }
        return false;
    }

    /**
     * @param key redis key, 唯一键
     * @return
     * @desc 释放锁 true已释放  false未释放
     */
    public void releaseLock(String key) {
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue)) {
                // 删除key
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("解锁出现异常了，{}", e);
        }
    }

    /**
     * 从hash中 获取value
     * @param mapKey
     * @param key
     * @return
     */
    public Object getHashValue(String mapKey, String key) {
        return redisTemplate.opsForHash().get(mapKey, key);
    }

}
