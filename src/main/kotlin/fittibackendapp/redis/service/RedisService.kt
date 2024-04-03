package fittibackendapp.redis.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    @Transactional
    fun setValue(key: String, value: Any) {
        redisTemplate.opsForValue().set(key, value)
    }

    @Transactional
    fun setValueWithExpire(key: String, value: Any, expire: Duration) {
        redisTemplate.opsForValue().set(key, value, expire)
    }

    fun getByKey(key: String): String {
        return redisTemplate.opsForValue().get(key) as String
    }

    @Transactional
    fun deleteByKey(key: String) {
        redisTemplate.delete(key)
    }

    fun checkByKey(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }
}
