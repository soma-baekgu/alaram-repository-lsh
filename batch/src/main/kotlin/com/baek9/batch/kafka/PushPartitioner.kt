package com.baek9.batch.kafka

import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.PartitionInfo
import org.apache.kafka.common.utils.Utils

class PushPartitioner : Partitioner {

    private val urgentKey: String = "urgent"
    private val reservedKey: String = "reserved"

    override fun configure(configs: MutableMap<String, *>?) {
    }

    override fun close() {
    }

    override fun partition(topic: String, key: Any, keyBytes: ByteArray, value: Any, valueBytes: ByteArray, cluster: Cluster): Int {
        val partitionsForTopic: List<PartitionInfo> = cluster.partitionsForTopic(topic)

        val numPartitions = partitionsForTopic.size
        val numUrgentPartitions = numPartitions / 4
        val numReservedPartitions = numPartitions - numUrgentPartitions
        val keyString: String = String(keyBytes)

        if(keyString == urgentKey)
            return Utils.toPositive(
                Utils.murmur2((keyString + System.currentTimeMillis().toString()).toByteArray())) % numUrgentPartitions

        else if(String(keyBytes) == reservedKey)
            return Utils.toPositive(
                Utils.murmur2((keyString + System.currentTimeMillis().toString()).toByteArray())) %
                    numReservedPartitions + numUrgentPartitions

        return 0
    }
}