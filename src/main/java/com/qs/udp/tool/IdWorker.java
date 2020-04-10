package com.qs.udp.tool;

import java.util.Random;

/**
 * 42位的时间前缀+10位的节点标识+12位的sequence避免并发的数字（12位不够用时强制得到新的时间前缀）
 * <p>
 * <b>对系统时间的依赖性非常强，需要关闭ntp的时间同步功能，或者当检测到ntp时间调整后，拒绝分配id。
 * 
 * @author 邢单欧
 * 
 */
public class IdWorker {

	private final long workerId;
	private final long snsEpoch = 1330328109047L;// 起始标记点，作为基准
	private long sequence = 0L;// 0，并发控制
	private final long workerIdBits = 10L;// 只允许workid的范围为：0-1023
	private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 1023,1111111111,10位
	private final long sequenceBits = 12L;// sequence值控制在0-4095

	private final long workerIdShift = this.sequenceBits;// 12
	private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 22
	private final long sequenceMask = -1L ^ -1L << this.sequenceBits;// 4095,111111111111,12位

	private long lastTimestamp = -1L;

	public IdWorker() {
		super();
		long min = 1;
		long max = 1023;
		long workerId = min + (((long) (new Random().nextDouble() * (max - min))));

		if (workerId > this.maxWorkerId || workerId < 0) {// workid < 1024[10位：2的10次方]
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
		}
		this.workerId = workerId;
	}

	public synchronized long nextId() throws Exception {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {// 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
			// System.out.println("lastTimeStamp:" + lastTimestamp);
			this.sequence = this.sequence + 1 & this.sequenceMask;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			System.out.println("Clock moved backwards.Refusing to generate id for " + (this.lastTimestamp - timestamp)
					+ "milliseconds.");
			throw new Exception(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds",
					(this.lastTimestamp - timestamp)));
		}

		this.lastTimestamp = timestamp;
		// 生成的timestamp
		return timestamp - this.snsEpoch << this.timestampLeftShift | this.workerId << this.workerIdShift
				| this.sequence;
	}

	/**
	 * 保证返回的毫秒数在参数之后
	 * 
	 * @param lastTimestamp
	 * @return
	 */
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	/**
	 * 获得系统当前毫秒数
	 * 
	 * @return
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 10000; i++) {
			IdWorker iw1 = new IdWorker();
			System.out.println(iw1.nextId());
		}

	}
}
