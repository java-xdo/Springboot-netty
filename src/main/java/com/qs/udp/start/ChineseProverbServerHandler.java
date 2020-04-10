package com.qs.udp.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qs.udp.redis.util.WaterMeterUdp;
import com.qs.udp.systematic.common.VoltmeterControllerPojo;
import com.qs.udp.tool.CheckLegality;
import com.qs.udp.voltmeter.controller.ValueController;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	private Logger log = LoggerFactory.getLogger(ChineseProverbServerHandler.class);

	/**
	 * 在这个方法中，形参packet客户端发过来的DatagramPacket对象
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		try {
			// 获取消息的ByteBuf
			ByteBuf buf = packet.copy().content();
			// 获取接收到的字节流转为string
			String hexValue = ByteBufUtil.hexDump(buf);
			byte[] bytes = new byte[buf.readableBytes()];// 将接收到的消息转化为byte，用来做报文验证
			buf.readBytes(bytes);
			log.info("客户端消息" + "==>" + hexValue);
			String deviceNum = hexValue.substring(5, 20);
			log.info(deviceNum);
			ValueController vc1 = new ValueController();
			String gatherKey = vc1.getGatherKey(deviceNum);

			VoltmeterControllerPojo.mapPacket.put(gatherKey, packet);
			VoltmeterControllerPojo.mapContext.put(gatherKey, ctx);
			// 执行抄表命令

//			String msg = "681008699750321698260000000085010000000000000000000000000000000000000000000000030101006116";
//			byte[] bytes1 = CheckLegality.getHexBytes(msg);
//			ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes1), packet.sender()));

			// 验证报文是否合法
			if (CheckLegality.checkMessage(bytes)) {
				byte len = bytes[14];// 控制码
				if (len == 0x02 || len == 0x03) {
					log.info("执行抄表命令");
					int head = WaterMeterUdp.head;
					int tail = WaterMeterUdp.tail;
					String adress = WaterMeterUdp.adress;
					String openSwitchBody = WaterMeterUdp.waterBanknote;
					String msg = head + adress + openSwitchBody + tail;
					byte[] bytes1 = CheckLegality.getHexBytes(msg);
					ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(bytes1), packet.sender()));
				}
				// 添加原始数据
				try {
					ValueController vc = new ValueController();
					vc.processingData(hexValue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				log.info("报文不合法，丢弃");
			}

			
		} catch (Exception e) {
			log.error("类 ChineseProverbServerHandler 出错" + e.getMessage());
		}
	}

}
