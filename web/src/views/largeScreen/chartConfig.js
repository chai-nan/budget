import * as echarts from "echarts";

export const threeDPieChart = {};
//南丁格尔（预算分析）
const colorList = [
  "#488CF7",
  "#38CAFB",
  "#4CAFF9",
  "#49DDC9",
  "#28A0E6",
  "#036DEA",
  "#183F86",
];
export const analyseChart = {
  legend: {
    show: false,
  },
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b} : {c} ({d}%)",
  },
  series: [
    {
      name: "预算分析",
      type: "pie",
      radius: ["15%", "50%"],
      center: ["50%", "50%"],
      roseType: "radius",
      label: {
        show: true,
        position: "outside",
        formatter: "{a|{b}\n{d}%}",
        rich: {
          hr: {
            backgroundColor: "t",
            borderRadius: 3,
            width: 3,
            height: 3,
            padding: [3, 3, 0, -12],
          },
          a: {
            padding: [-30, 15, -20, 15],
            lineHeight: 20,
          },
        },
      },
      labelLine: {
        length: 3,
        length2: 5,
        // smooth: true,
      },
      data: [
        {
          value: 6,
          name: "rose1",
          label: {
            show: true,
            color: colorList[0],
          },
          itemStyle: {
            color: colorList[0],
          },
        },
        {
          value: 4,
          name: "rose2",
          label: {
            show: true,
            color: colorList[1],
          },
          itemStyle: {
            color: colorList[1],
          },
        },
        {
          value: 3,
          name: "rose3",
          label: {
            show: true,
            color: colorList[2],
          },
          itemStyle: {
            color: colorList[2],
          },
        },
        {
          value: 2,
          name: "rose4",
          label: {
            show: true,
            color: colorList[3],
          },
          itemStyle: {
            color: colorList[3],
          },
        },
        {
          value: 1,
          name: "rose5",
          label: {
            show: true,
            color: colorList[4],
          },
          itemStyle: {
            color: colorList[4],
          },
        },
        {
          value: 2,
          name: "rose5",
          label: {
            show: true,
            color: colorList[5],
          },
          itemStyle: {
            color: colorList[5],
          },
        },
      ],
    },
  ],
};

//渐变边框柱图（预算统计）
export const statisticsChart = {
  tooltip: {
    show: true,
    // formatter: function (params) {
    //     console.log(params)
    //   let text =
    //     '<p  style="font-size:16px;font-weight: 400;color:rgba(255, 255, 255, 1);"><span style="display:inline-block;width:10px;height: 10px;background: ' +
    //     data.color[params.seriesName=='要案数'?0:1]+ ';border-radius: 50%;margin-right: 10px;"></span>' + params
    //     .name + '<br>'+params.seriesName+':' + params.data + '件</p>';

    //   return text
    // },
    backgroundColor: "rgba(38, 68, 110, 0.8)",
    padding: 10,
    borderColor: "rgba(38, 68, 110, 1)",
    textStyle: {
      color: "#fff",
    },
  },
  grid: {
    top: "15%",
    left: "6%",
    right: "5%",
    bottom: "15%",
  },
  legend: {
    show: false,
  },
  xAxis: [
    {
      type: "category",
      axisLine: {
        lineStyle: {
          color: "rgba(255, 255, 255, 0)",
        },
      },
      axisLabel: {
        textStyle: {
          fontSize: 10,
          color: "rgba(255, 255, 255, 0.6)",
        },
      },
      axisTick: {
        show: false,
      },
      data: [
        "郑州股份",
        "郑州区域",
        "郑州集团",
        "郑州部门",
        "郑州股份",
        "郑州区域",
        "郑州集团",
        "郑州部门",
      ],
    },
  ],
  yAxis: {
    name: "单位:万元",
    nameTextStyle: {
      color: "rgba(255,255,255,0.5)",
      fontSize: 10,
      align: "right",
      lineHeight: 16,
    },
    // max: 5,
    splitNumber: 5,
    type: "log",
    logBase: 10,
    axisTick: {
      show: false,
    },
    axisLine: {
      show: false,
    },
    axisLabel: {
      show: false,
    },
    splitLine: {
      show: true,
      lineStyle: {
        color: "#8EEEFF",
        opacity: 0.2,
        type: "dashed",
      },
    },
  },

  // ... existing code ...
  series: [
    {
      name: "预算费用",
      type: "bar",
      barWidth: "30%",
      data: [1, 2, 3, 4, 5, 6, 5, 4, 3, 2],
      label: {
        show: true,
        formatter: (params) => {
          return params.value;
        },
        textStyle: {
          fontSize: 10,
          // fontFamily: "zcool-gdh",
          color: "rgba(21, 219, 203, 1)",
        },
        lineHeight: 20,
        position: "top",
        // emphasis: {
        //   show: true,
        // },
      },
      itemStyle: {
        borderColor: "rgba(21, 219, 203, 1)",
        borderWidth: 1,
        color: new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          [
            {
              offset: 0,
              color: "rgba(21, 219, 203, 0.5)",
            },
            {
              offset: 1,
              color: "rgba(15, 51, 82, 0.5)",
            },
          ],
          false
        ),
      },
    },
    {
      name: "实际费用",
      type: "bar",
      barWidth: "30%",
      data: [0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 4.5, 3.5, 2.5, 1.5],
      label: {
        show: true,
        formatter: (params) => {
          return params.value;
        },
        textStyle: {
          fontSize: 10,
          // fontFamily: "zcool-gdh",
          color: "rgb(21, 219, 203, 1)",
        },
        lineHeight: 20,
        position: "top",
      },
      itemStyle: {
        borderColor: "rgba(21, 219, 203, 1)",
        borderWidth: 1,
        color: new echarts.graphic.LinearGradient(
          0,
          0,
          0,
          1,
          [
            {
              offset: 0,
              color: "rgba(21, 219, 203, 0.5)",
            },
            {
              offset: 1,
              color: "rgba(15, 51, 82, 0.5)",
            },
          ],
          false
        ),
      },
    },
  ],
  // ... existing code ...
};

//渐变边框柱图（预算统计）
export const statisticsChart1 = {
  //   backgroundColor: "#021132",
  tooltip: {
    trigger: "axis",
    formatter: function (params) {
          var str = params[0].name + "<br/>";
          for (var k = 0; k < params.length; k++) {
            if (params[k].seriesName) {
              str +=
                params[k].seriesName + "  :  " + formatNumber((params[k].value - 1).toFixed(2)) + "万元" + "<br/>";
            }
          }
          return str;
        },
    textStyle: {
      // 提示框浮层的文本样式。
      color: "#fff",
      fontStyle: "normal",
      fontWeight: "normal",
      fontFamily: "sans-serif",
      fontSize: 14,
    },
    axisPointer: {
      type: "shadow",
    },
    extraCssText:
      "color:#fff;border:1px solid #eee;background:rgba(11,56,109,0.8);box-shadow:rgb(132, 255, 255) 0px 0px 10px inset;",
  },
  grid: {
    left: "15%",
    bottom: "15%",
    right: "10%",
    top: "10%",
  },
  xAxis: {
    data: [],
    show: true,
    axisTick: {
      show: false,
    },
    axisLine: {
      show: true,
      lineStyle: {
        type: "solid",
        color: "gray",
        opacity: 0.5,
      },
    },
    // lineStyle: {
    //     show: true,
    //     type: 'solid'
    // },
    axisLabel: {
      interval: 0,
      textStyle: {
        color: "#fff",
        fontSize: 10,
      },
      rotate: 15,
      margin: 20, //刻度标签与轴线之间的距离。
    },
  },
  yAxis: {
    name: "单位:万元",
    type: "log",
    logBase: 10,
    nameTextStyle: {
      color: "rgba(255,255,255,0.5)",
      fontSize: 10,
      align: "right",
      lineHeight: 16,
    },
    splitLine: {
      show: true,
      lineStyle: {
        type: "dashed",
        opacity: 0.5,
      },
    },
    axisTick: {
      show: false,
    },
    axisLine: {
      show: true,
    },
    axisLabel: {
      textStyle: {
        color: "#fff",
        fontSize: 10,
      },
    },
  },
  series: [
    {
      //三个最低下的圆片
      name: "",
      type: "pictorialBar",
      symbolSize: [20, 10],
      symbolOffset: [0, 5],
      z: 12,
      itemStyle: {
        opacity: 1,
        color: function (params) {
          var a = params.name.slice(0, 2);
          return new echarts.graphic.LinearGradient(
            0,
            0,
            0,
            1,
            [
              {
                offset: 0,
                color: "#12B9DB", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#6F8EF2", // 100% 处的颜色
              },
            ],
            false
          );
        },
      },
      data: [1, 1, 1, 1, 1, 1],
    },

    //下半截柱状图
    {
      name: "",
      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      itemStyle: {
        //lenged文本
        opacity: 0.7,
        color: function (params) {
          var a = params.name.slice(0, 2);

          return new echarts.graphic.LinearGradient(
            0,
            0,
            0,
            1,
            [
              {
                offset: 0,
                color: "#12B9DB", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#6F8EF2", // 100% 处的颜色
              },
            ],
            false
          );
        },
      },

      data: [200, 100, 200, 200, 100, 123],
    },

    {
      // 替代柱状图 默认不显示颜色，是最下方柱图（邮件营销）的value值 - 20
      name: "",

      type: "bar",
      show: false,
      barWidth: 20,
      barGap: "-100%",
      stack: "广告",
      itemStyle: {
        color: "transparent",
      },
      data: [200, 100, 200, 200, 100, 123],
    },

    {
      name: "", //头部
      type: "pictorialBar",
      symbolSize: [20, 10],
      symbolOffset: [0, -5],
      z: 12,
      symbolPosition: "end",
      itemStyle: {
        color: "#163F7A",
        opacity: 1,
      },
      data: [300, 200, 300, 300, 400, 143],
    },

    {
      name: "",
      type: "pictorialBar",
      symbolSize: [20, 10],
      symbolOffset: [0, -5],
      z: 12,
      itemStyle: {
        opacity: 1,
        color: function (params) {
          var a = params.name.slice(0, 2);

          return new echarts.graphic.LinearGradient(
            0,
            0,
            0,
            1,
            [
              {
                offset: 0,
                color: "#12B9DB", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#6F8EF2", // 100% 处的颜色
              },
            ],
            false
          );
        },
      },
      symbolPosition: "end",
      data: [200, 100, 200, 200, 100, 123],
    },

    {
      name: "",
      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      z: 0,
      itemStyle: {
        color: "#163F7A",
        opacity: 0.7,
      },

      data: [300, 200, 300, 300, 400, 143],
    },
  ],
};

//堆叠渐变柱图（预算费用/实际费用）
export const costChart = {
  //   backgroundColor: "#021132",
  tooltip: {
    trigger: "axis",
    formatter: function (params) {
      var str = params[0].name + "<br/>";;
          for (var k = 0; k < params.length; k++) {
            if (params[k].seriesName) {
              str +=
                params[k].seriesName + "  :  " + formatNumber((params[k].value - 1).toFixed(2)) + "万元" + "<br/>";
            }
          }
          return str;
        },
    textStyle: {
      // 提示框浮层的文本样式。
      color: "#fff",
      fontStyle: "normal",
      fontWeight: "normal",
      fontFamily: "sans-serif",
      fontSize: 14,
    },
    axisPointer: {
      type: "shadow",
    },
    extraCssText:
      "color:#fff;border:1px solid #eee;background:rgba(11,56,109,0.8);box-shadow:rgb(132, 255, 255) 0px 0px 10px inset;",
  },
  grid: {
    left: "15%",
    bottom: "15%",
    right: "10%",
    top: "10%",
  },
  xAxis: {
    data: ["员工成本(去年)", "员工成本", "资产投资(去年)", "资产投资", "安全生产(去年)", "安全生产", "业务拓展(去年)", "业务拓展", "日常生产(去年)", "日常生产", "其它(去年)", "其它"],
    show: true,
    axisTick: {
      show: false,
    },
    axisLine: {
      show: true,
      lineStyle: {
        type: "solid",
        color: "gray",
        opacity: 0.5,
      },
    },
    // lineStyle: {
    //     show: true,
    //     type: 'solid'
    // },
    axisLabel: {
      interval: 0,
      textStyle: {
        color: "#fff",
        fontSize: 10,
      },
      rotate: 20,
      margin: 20, //刻度标签与轴线之间的距离。
    },
  },
  yAxis: {
    name: "单位:万元",
    type: "log",
    logBase: 10,
    nameTextStyle: {
      color: "rgba(255,255,255,0.5)",
      fontSize: 10,
      align: "right",
      lineHeight: 16,
    },
    splitLine: {
      show: true,
      lineStyle: {
        type: "dashed",
        opacity: 0.5,
      },
    },
    
    axisTick: {
      show: false,
    },
    axisLine: {
      show: true,
    },
    axisLabel: {
      textStyle: {
        color: "#fff",
        fontSize: 10,
      },
    },
  },
  series: [
    // 最低下的圆片
    {
      name: "",
      type: "pictorialBar",
      symbolSize: [20, 10],
      symbolOffset: [0, 5],
      z: 12,
      itemStyle: {
        opacity: 1,
        color: function (params) {
          // 根据数据索引的奇偶性设置不同颜色
          if (params.dataIndex % 2 === 1) {
            // 奇数位保持原有颜色
            return new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: "#12B9DB", // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "#6F8EF2", // 100% 处的颜色
                },
              ],
              false
            );
          } else {
            // 偶数位改为红色
            return new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: "#23fffc", // 红色 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "#23fffc", // 橙红色 100% 处的颜色
                },
              ],
              false
            );
          }
        },
      },
      data: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    },

    //下半截柱状图
    {
      name: "实际费用",
      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      itemStyle: {
        //lenged文本
        opacity: 0.7,
        color: function (params) {
          // 根据数据索引的奇偶性设置不同颜色
          if (params.dataIndex % 2 === 1) {
            // 奇数位保持原有颜色
            return new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: "#12B9DB", // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "#6F8EF2", // 100% 处的颜色
                },
              ],
              false
            );
          } else {
            // 偶数位改为红色
            return new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: "#23fffc", // 红色 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "#23fffc", // 橙红色 100% 处的颜色
                },
              ],
              false
            );
          }
        },
      },

      data: [200, 100, 200, 200, 100, 120, 200, 100, 200, 200, 100, 120],
    },
    // 替代柱状图 默认不显示颜色，是最下方柱图（邮件营销）的value值 - 20
    {
      name: "",

      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      stack: "广告",
      itemStyle: {
        color: "transparent",
      },
      data: [200, 100, 200, 200, 100, 120, 200, 100, 200, 200, 100, 120],
    },

    //头部
    {
      name: "预算费用",
      type: "pictorialBar",
      symbolSize: [20, 10],
      symbolOffset: [0, -5],
      z: 12,
      symbolPosition: "end",
      itemStyle: {
        color: function (params) {
          // 根据数据索引的奇偶性设置不同颜色
          if (params.dataIndex % 2 === 1) {
            // 奇数位保持原有颜色
            return "#163F7A";
          } else {
            // 偶数位改为红色
            return "#0a505e";
          }
        },
        opacity: 1,
      },
      data: [300, 200, 300, 300, 400, 140, 300, 200, 300, 300, 400, 140],
    },

    {
      name: "",
      type: "pictorialBar",
      symbolSize: [20, 10],
      symbolOffset: [0, -5],
      z: 12,
      itemStyle: {
        opacity: 1,
        color: function (params) {
          // 根据数据索引的奇偶性设置不同颜色
          if (params.dataIndex % 2 === 1) {
            // 奇数位保持原有颜色
            return new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: "#12B9DB", // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "#6F8EF2", // 100% 处的颜色
                },
              ],
              false
            );
          } else {
            // 偶数位改为红色
            return new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: "#23fffc", // 红色 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "#23fffc", // 橙红色 100% 处的颜色
                },
              ],
              false
            );
          }
        },
      },
      symbolPosition: "end",
      data: [200, 100, 200, 200, 100, 120, 200, 100, 200, 200, 100, 120],
    },

    {
      name: "",
      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      z: 0,
      itemStyle: {
        color: function (params) {
          // 根据数据索引的奇偶性设置不同颜色
          if (params.dataIndex % 2 === 1) {
            // 奇数位保持原有颜色
            return "#163F7A";
          } else {
            // 偶数位改为红色
            return "#0a505e";
          }
        },
        opacity: 0.7,
      },

      data: [300, 200, 300, 300, 400, 140, 300, 200, 300, 300, 400, 140],
    },
  ],
};


// 千分位格式化函数
function formatNumber(num) {
  return new Intl.NumberFormat('zh-CN').format(num);
}

//堆叠渐变柱图（三年数据）
export const threeYearChart = {
  //   backgroundColor: "#021132",
  tooltip: {
    trigger: "axis",
    formatter: function (params) {
          var str = params[0].name + "年" + "<br/>";
          for (var k = 0; k < params.length; k++) {
            if (params[k].seriesName) {
              str +=
                params[k].seriesName + "  :  " + formatNumber((params[k].value - 1).toFixed(2)) + "万元" + "<br/>";

            }
          }
          return str;
        },
    textStyle: {
      // 提示框浮层的文本样式。
      color: "#fff",
      fontStyle: "normal",
      fontWeight: "normal",
      fontFamily: "sans-serif",
      fontSize: 14,
    },
    axisPointer: {
      type: "shadow",
    },
    extraCssText:
      "color:#fff;border:1px solid #eee;background:rgba(11,56,109,0.8);box-shadow:rgb(132, 255, 255) 0px 0px 10px inset;",
  },
  grid: {
    left: "15%",
    bottom: "15%",
    right: "10%",
    top: "10%",
  },
  xAxis: {
    name: "单位:万元",
    nameTextStyle: {
      color: "rgba(255,255,255,0.5)",
      fontSize: 10,
      align: "right",
      lineHeight: 16,
    },
    splitLine: {
      show: true,
      lineStyle: {
        type: "dashed",
        opacity: 0.5,
      },
    },
    axisTick: {
      show: false,
    },
    axisLine: {
      show: true,
    },
    axisLabel: {
      textStyle: {
        color: "#fff",
        fontSize: 10,
      },
    },
  },
  yAxis: {
    type: 'category',
    data: [],
    show: true,
    axisTick: {
      show: false,
    },
    axisLine: {
      show: true,
      lineStyle: {
        type: "solid",
        color: "gray",
        opacity: 0.5,
      },
    },
    axisLabel: {
      interval: 0,
      textStyle: {
        color: "#fff",
        fontSize: 10,
      },
      margin: 20, //刻度标签与轴线之间的距离。
    },
  },
  series: [

    // 最低下的圆片
    {
      name: "",
      type: "pictorialBar",
      symbolSize: [10, 20], // 宽高
      symbolOffset: [-5, 0], // 偏移量
      z: 12,
      itemStyle: {
        opacity: 1,
        color: function (params) {
          var a = params.name.slice(0, 2);
          return new echarts.graphic.LinearGradient(
            0,
            0,
            1,
            0, // 交换渐变方向
            [
              {
                offset: 0,
                color: "#12B9DB", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#6F8EF2", // 100% 处的颜色
              },
            ],
            false
          );
        },
      },
      data: [0.5, 0.5, 0.5],
    },

    //下半截柱状图
    {
      name: "",
      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      itemStyle: {
        //lenged文本
        opacity: 0.7,
        color: function (params) {
          var a = params.name.slice(0, 2);

          return new echarts.graphic.LinearGradient(
            0,
            0,
            1,
            0, // 交换渐变方向
            [
              {
                offset: 0,
                color: "#12B9DB", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#6F8EF2", // 100% 处的颜色
              },
            ],
            false
          );
        },
      },
      data: [200, 100, 200],
    },

    {
      // 替代柱状图 默认不显示颜色，是最下方柱图（邮件营销）的value值 - 20
      name: "",

      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      stack: "广告",
      itemStyle: {
        color: "transparent",

      },
      data: [200, 100, 200],
    },

    {
      name: "", //头部
      type: "pictorialBar",
      symbolSize: [10, 20], // 宽高
      symbolOffset: [5, 0], // 偏移量
      z: 12,
      symbolPosition: "end",
      itemStyle: {
        color: "#163F7A",
        opacity: 1,
      },
      data: [300, 200, 300],
    },

    {
      name: "",// 中部圆片
      type: "pictorialBar",
      symbolSize: [10, 20], // 宽高
      symbolOffset: [5, 0], // 偏移量
      z: 12,
      itemStyle: {
        opacity: 1,
        color: function (params) {
          var a = params.name.slice(0, 2);
          return new echarts.graphic.LinearGradient(
            0,
            0,
            1,
            0, // 交换渐变方向
            [
              {
                offset: 0,
                color: "#12B9DB", // 0% 处的颜色
              },
              {
                offset: 1,
                color: "#6F8EF2", // 100% 处的颜色
              },
            ],
            false
          );
        },
      },
      symbolPosition: "end",
      data: [200, 100, 200],
    },

    {
      name: "",
      type: "bar",
      barWidth: 20,
      barGap: "-100%",
      z: 0,
      itemStyle: {
        color: "#163F7A",
        opacity: 0.7,
      },

      data: [300, 200, 300],
    },
  ],
};

//大屏子页面
//预算金额与支出金额分析柱图
export const yszcChart = {
  // backgroundColor: "#0b1146",
  grid: {
    left: "8%",
    right: "2%",
    top: "15%",
    bottom: "20%",
  },
  tooltip: {
    trigger: "axis",
    axisPointer: {
      type: "shadow",
      lineStyle: {
        color: "#fff",
      },
    },
  },
  legend: {
    bottom: "1%",
    left: "center",
    textStyle: {
      color: "#5195da",
    },
  },
  xAxis: {
    type: "category",

    axisLine: {
      show: true,
      lineStyle: {
        color: "#3585d5",
      },
    },
    // offset: 10,
    axisTick: {
      show: false,
      alignWithLabel: true,
      lineStyle: {
        color: "#3585d5",
      },
    },
    axisLabel: {
      fontSize: 12,
      color: "#A2D4E6",
    },
    data: [],
  },
  yAxis: {
    // name: "单位",
    nameTextStyle: {
      color: "#A2D4E6",
    },
    type: "value",
    axisLine: {
      show: true,
      lineStyle: {
        color: "#3585d5",
      },
    },
    splitLine: {
      show: false,
      lineStyle: {
        type: "dotted",
        color: "#3585d5",
      },
    },
    axisTick: {
      show: false,
    },
    axisLabel: {
      fontSize: 12,
      color: "#A2D4E6",
    },
    boundaryGap: ["20%", "20%"],
    min: 0,
  },
  series: [
    {
      name: "预算金额",
      type: "bar",
      smooth: true,
      symbol: "none",
      showSymbol: false,
      symbolSize: 8,
      itemStyle: {
        color: "#3269DB",
      },
      data: [],
    },
    {
      name: "支出金额",
      type: "bar",
      smooth: true,
      symbol: "none",
      showSymbol: false,
      symbolSize: 8,
      itemStyle: {
        color: "#33D3FF",
      },
      data: [],
    },
  ],
};

// 实际费用分析
export const sjfyfxChart = {
  tooltip: {
    trigger: 'axis',
    formatter: function (params) {
      var str = params[0].name + "<br/>";
      for (var k = 0; k < params.length; k++) {
        if (params[k].seriesName) {
          str +=
            params[k].seriesName + "  :  " + formatNumber(params[k].value) + "万元" + "<br/>";
        }
      }
      return str;
    },
  },
  color: ['#163F7A', '#12B9DB'],
  legend: {
    data: []
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['第一季度', '第二季度', '第三季度', '第四季度']
  },
  yAxis: {
    type: 'value',
    name: '金额 (万元)',
    axisLabel: {
      formatter: '{value}'
    }
  },
  series: [
    {
      name: '2025',
      type: 'line',
      data: [120, 132, 101, 134],
      lineStyle: {
        width: 3
      },
      itemStyle: {
        color: '#163F7A'
      },
      symbol: 'circle',
      symbolSize: 8
    },
    {
      name: '2024',
      type: 'line',
      data: [220, 182, 191, 234],
      lineStyle: {
        width: 3
      },
      itemStyle: {
        color: '#12B9DB'
      },
      symbol: 'circle',
      symbolSize: 8
    }
  ]
}


//支出同比分析饼图
export const zctbChart = {
  // backgroundColor: "#364686",
  color: ["#032D3D", "#46AE7D"],
  title: [
    {
      text: "",
      x: "center",
      top: "36%",
      textStyle: {
        color: "#00FFE4",
        fontSize: 14,
        fontWeight: "500",
      },
    },
    {
      text: "",
      x: "center",
      top: "48%",
      textStyle: {
        fontSize: 16,
        color: "#00FFE4",
        foontWeight: "500",
      },
    },
  ],
  polar: {
    // radius: ["44%", "50%"],
    // center: ["50%", "50%"],
  },
  angleAxis: {
    max: 100,
    show: false,
  },
  radiusAxis: {
    type: "category",
    show: true,
    axisLabel: {
      show: false,
    },
    axisLine: {
      show: false,
    },
    axisTick: {
      show: false,
    },
  },
  series: [
    {
      type: "pie",
      radius: ["65%", "80%"],
      center: ["50%", "50%"],
      startAngle: 0,
      data: [
        // {
        //   name: "本年度支出",
        //   value: "3720",
        // },
        // {
        //   name: "去年度支出",
        //   value: "2920",
        // },
      ],
      hoverAnimation: false,
      itemStyle: {
        // normal: {
        borderColor: "#364684",
        borderWidth: 2,
        // },
      },
      labelLine: {
        // normal: {
        length: 40,
        length2: 50,
        lineStyle: {
          color: "rgba(0,0,0,0)",
        },
        // },
      },
      label: {
        // normal: {
        formatter: (params) => {
          return (
            "{icon|●}{name|" +
            params.name +
            "}\n{value|" +
            params.value +
            "万元" +
            "}"
          );
        },
        padding: [0, -70, 30, -70],
        rich: {
          icon: {
            fontSize: 0,
          },
          name: {
            fontSize: 12,
            align: "left",
            padding: [-30, 0, 0, 0],
            color: "#999",
          },
          value: {
            fontSize: 14,
            fontWeight: "bold",
            color: "#999",
            align: "left",
          },
        },
        // },
      },
    },
    {
      name: "",
      type: "pie",
      startAngle: 90,
      radius: "60%",
      hoverAnimation: false,
      center: ["50%", "50%"],
      itemStyle: {
        // normal: {
        labelLine: {
          length: 40,
          length2: 50,
          lineStyle: {
            color: "rgba(0,0,0,0)",
          },
        },
        color: new echarts.graphic.RadialGradient(0.5, 0.5, 1, [
          {
            offset: 1,
            color: "rgba(50,171,241, 1)",
          },
          {
            offset: 0,
            color: "rgba(55,70,130, 0)",
          },
        ]),
        // borderWidth: 1,
        // borderColor: '',
        shadowBlur: 10,
        // shadowColor: 'rgba(55,70,130, 1)'
        // },
      },
      data: [
        {
          value: 100,
        },
      ],
    },
  ],
};
