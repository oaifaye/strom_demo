package test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

/**
 * ������һ���򵥵�topology������һ�������緢�ڵ�spout��һ�����ݴ���ڵ�bolt��
 * 
 * @author Administrator
 *
 */
public class SimpleTopology {
    public static void main(String[] args) {
        try {
            // ʵ����TopologyBuilder�ࡣ
            TopologyBuilder topologyBuilder = new TopologyBuilder();
            // �����緢�ڵ㲢���䲢�������ò�����������Ƹö����ڼ�Ⱥ�е��߳�����
            topologyBuilder.setSpout("SimpleSpout", new SimpleSpout(), 1);
            // �������ݴ���ڵ㲢���䲢������ָ���ýڵ�����緢�ڵ�Ĳ���Ϊ�����ʽ��
            topologyBuilder.setBolt("SimpleBolt", new SimpleBolt(), 3).shuffleGrouping("SimpleSpout");
            Config config = new Config();
            config.setDebug(true);
            if (args != null && args.length > 0) {
                config.setNumWorkers(1);
                StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
            } else {
                // �����Ǳ���ģʽ�����е��������롣
                config.setMaxTaskParallelism(1);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("simple", config, topologyBuilder.createTopology());
            }
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
