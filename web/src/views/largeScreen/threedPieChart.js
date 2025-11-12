import * as echarts from "echarts";
import "echarts-gl";
import "echarts/lib/chart/bar";

// 生成扇形的曲面参数方程，用于 series-surface.parametricEquation
function getParametricEquation(
  startRatio,
  endRatio,
  isSelected,
  isHovered,
  k,
  h,
  i
) {
  // 计算
  let midRatio = (startRatio + endRatio) / 2;

  let startRadian = startRatio * Math.PI * 2;
  let endRadian = endRatio * Math.PI * 2;
  let midRadian = midRatio * Math.PI * 2;

  // 如果只有一个扇形，则不实现选中效果。
  if (startRatio === 0 && endRatio === 1) {
    isSelected = false;
  }

  // 通过扇形内径/外径的值，换算出辅助参数 k（默认值 1/3）
  k = typeof k !== "undefined" ? k : 1 / 3;

  // 计算选中效果分别在 x 轴、y 轴方向上的位移（未选中，则位移均为 0）
  let offsetX = isSelected ? Math.cos(midRadian) * 0.15 : 0;
  let offsetY = isSelected ? Math.sin(midRadian) * 0.1 : 0;
  let offsetZ = i == 1 ? 2 : 0;
  console.log("offsetX", i, offsetX, offsetY);
  // 计算高亮效果的放大比例（未高亮，则比例为 1）
  let hoverRate = isHovered ? 1.1 : 1;

  // 返回曲面参数方程
  return {
    u: {
      min: -Math.PI,
      max: Math.PI * 2,
      step: Math.PI / 32,
    },

    v: {
      min: 0,
      max: Math.PI * 2,
      step: Math.PI / 32,
    },

    x: function (u, v) {
      if (u < startRadian) {
        return (
          offsetX + Math.cos(startRadian) * (1 + Math.cos(v) * k) * hoverRate
        );
      }
      if (u > endRadian) {
        return (
          offsetX + Math.cos(endRadian) * (1 + Math.cos(v) * k) * hoverRate
        );
      }
      return offsetX + Math.cos(u) * (1 + Math.cos(v) * k) * hoverRate;
    },

    y: function (u, v) {
      if (u < startRadian) {
        return (
          offsetY + Math.sin(startRadian) * (1 + Math.cos(v) * k) * hoverRate
        );
      }
      if (u > endRadian) {
        return (
          offsetY + Math.sin(endRadian) * (1 + Math.cos(v) * k) * hoverRate
        );
      }
      return offsetY + Math.sin(u) * (1 + Math.cos(v) * k) * hoverRate;
    },

    z: function (u, v) {
      if (u < -Math.PI * 0.5) {
        return offsetZ + Math.sin(u);
      }
      if (u > Math.PI * 2.5) {
        return offsetZ + Math.sin(u);
      }
      // 调整扇形高度
      return offsetZ + (Math.sin(v) > 0 ? 0.1 * h : -1);
    },
  };
}

// 生成模拟 3D 饼图的配置项
function getPie3D(pieData, internalDiameterRatio, mNum, lineSize) {
  let series = [];
  let sumValue = 0;
  let startValue = 0;
  let endValue = 0;
  let legendData = [];
  let fakeData = 10;
  let linesSeries = []; // line3D模拟label指示线
  let sumState = false
  let k =
    typeof internalDiameterRatio !== "undefined"
      ? (1 - internalDiameterRatio) / (1 + internalDiameterRatio)
      : 1 / 3;

  // 为每一个饼图数据，生成一个 series-surface 配置
  for (let i = 0; i < pieData.length; i++) {
    if (pieData[i].value !== 0.12138) {
      sumState = true
    }
    sumValue += pieData[i].value;

    let seriesItem = {
      name:
        typeof pieData[i].name === "undefined" ? `series${i}` : pieData[i].name,
      type: "surface",
      parametric: true,
      wireframe: {
        show: false,
      },
      pieData: pieData[i],
      pieStatus: {
        selected: false,
        hovered: false,
        k: 1 / 10,
      },
    };

    if (typeof pieData[i].itemStyle != "undefined") {
      let itemStyle = {};

      typeof pieData[i].itemStyle.color != "undefined"
        ? (itemStyle.color = pieData[i].itemStyle.color)
        : null;
      typeof pieData[i].itemStyle.opacity != "undefined"
        ? (itemStyle.opacity = pieData[i].itemStyle.opacity)
        : null;

      seriesItem.itemStyle = itemStyle;
    }
    series.push(seriesItem);
  }

  // 使用上一次遍历时，计算出的数据和 sumValue，调用 getParametricEquation 函数，
  // 向每个 series-surface 传入不同的参数方程 series-surface.parametricEquation，也就是实现每一个扇形。
  for (let i = 0; i < series.length; i++) {
    endValue = startValue + series[i].pieData.value;

    series[i].pieData.startRatio = startValue / sumValue;
    series[i].pieData.endRatio = endValue / sumValue;
    series[i].parametricEquation = getParametricEquation(
      series[i].pieData.startRatio,
      series[i].pieData.endRatio,
      true,
      false,
      k,
      // 调整扇形高度
      i === 0 ? 10 : 10,
      i
    );

    startValue = endValue;
    // 计算label指示线的起始和终点位置
    let midRadian =
      (series[i].pieData.endRatio + series[i].pieData.startRatio) * Math.PI;
    let posX = Math.cos(midRadian) * (1.2 + Math.cos(Math.PI / 2));
    let posY = Math.sin(midRadian) * (0.9 + Math.cos(Math.PI / 2));
    // 改动：将指示线起点的Z坐标提高到饼图上方
    // let posZ = Math.log(Math.abs(fakeData + 200)) * 0.1; // 原代码

    // let posZ = 3.5; // 新代码：固定值，确保在饼图上方
    // 为每个扇形单独设置 posZ 值
    let posZ;
    if (pieData[i].name === "达标天数") {
      posZ = 1.4; // 达标天数的指示线起点高度
    } else {
      posZ = 3.4; // 未达标天数的指示线起点高度
    }

    //let flag = ((midRadian >= 0 && midRadian <= Math.PI / 2) || (midRadian >= 3 * Math.PI / 2 && midRadian <= Math.PI * 2)) ? 1 : -1;
    // 改动：根据索引决定方向，偶数索引向右，奇数索引向左
    let flag = i % 2 === 0 ? -1 : 1; // 偶数向右，奇数向左

    //分别设置左右指示线的 水平方向长度    1是左边  0.7是右边
    let flaglevellength = i % 2 === 0 ? 1.2 : 1; // 偶数向右，奇数向左

    //分别设置左右指示线的 垂直方向长度    1是左边   0.9是右边
    let flagverticallength = i % 2 === 0 ? 0.6 : 0.55; // 偶数向右，奇数向左

    let color = pieData[i].itemStyle.color;

    // 改动：调整转折点和终点的位置计算
    let turningPosArr = [
      posX + 0.3 * flag,
      posY,
      (posZ + 8) * flagverticallength,
    ]; // 水平转折
    let endPosArr = [
      posX + 2.8 * flag * flaglevellength,
      posY,
      (posZ + 8) * flagverticallength,
    ]; // 终点位置

    // 改动：调整转折点和终点的Z坐标，确保它们也在饼图上方
    // let turningPosArr = [posX * (1.8) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posY * (1.8) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posZ * (2)]; // 原代码
    //let turningPosArr = [posX * (1.8) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posY * (1.8) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posZ + 2]; // 新代码

    // let endPosArr = [posX * (1.9) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posY * (1.9) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posZ * (6)]; // 原代码
    //let endPosArr = [posX * (1.9) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posY * (1.9) + (i * 0.1 * flag) + (flag < 0 ? -0.5 : 0), posZ + 4]; // 新代码

    // 添加指示线起点的圆点
    linesSeries.push({
      type: "scatter3D",
      symbol: "circle",
      symbolSize: 6,
      itemStyle: {
        color: "#fff",
      },
      data: [[posX, posY, posZ]],
    });

    // 添加指示线
    linesSeries.push({
      type: "line3D",
      lineStyle: {
        color: "#fff",
        width: 2,
      },
      data: [[posX, posY, posZ], turningPosArr, endPosArr],
    });

    // 添加指示线终点的圆点
    linesSeries.push({
      type: "scatter3D",
      symbol: "circle",
      symbolSize: 6,
      itemStyle: {
        color: "#fff",
      },
      data: [endPosArr],
    });

    // 添加标签（去掉背景颜色）
    linesSeries.push({
      type: "scatter3D",
      label: {
        show: true,
        distance: -10,
        position: flag > 0 ? "left" : "right",
        textStyle: {
          color: "#fff",
          backgroundColor: "transparent", // 去掉背景颜色
          borderWidth: 0, // 去掉边框
          fontSize: 16,
          padding: [0, 10, 40, 10],
          borderRadius: 4,
        },
        rich: {
          // 定义数值样式
          value: {
            fontSize: lineSize + 2,
            fontWeight: 900,
            color: "#41e2ff", // 使用扇形的颜色
          },
          // 定义单位和名称样式
          unit: {
            fontSize: lineSize - 2,
            color: "#fff",
            padding: [0, 0, 0, 4], // 添加左边距
          },
          name: {
            fontSize: lineSize - 2,
            color: "#fff",
            lineHeight: lineSize - 2, // 控制换行后的行高
          },
        },
        // 使用富文本标签格式化内容
        formatter: function (params) {
          return `{value|${series[i].pieData.value}}{unit|(万元)}\n{name|${series[i].name}}`;
        },
      },
      symbolSize: 0,
      data: [
        {
          name: series[i].pieData.value + "(万元)" + "\n" + series[i].name,
          value: endPosArr,
        },
      ],
    });

    legendData.push(series[i].name);
  }
  if (!sumState) {
    // console.log('所有都0');
    for (let i = 0; i < pieData.length; i++){
      series[i].pieData.value=1
      series[i].pieData.itemStyle.color='red'
    }
  } else {
    series = series.concat(linesSeries);
  }
  // series = series.concat(linesSeries);
  // console.log(series, '配置项');
  

  // 准备待返回的配置项，把准备好的 legendData、series 传入。
  let option = {
    // backgroundColor: "#110B2A", // 设置背景为透明
    //animation: false,
    legend: {
      show: false, // 禁用图例显示
    },
    xAxis3D: {
      min: -mNum,
      max: mNum,
    },
    yAxis3D: {
      min: -mNum,
      max: mNum,
    },
    zAxis3D: {
      min: -mNum,
      max: mNum,
    },
    grid3D: {
      show: false,
      boxHeight: 14,
      left: "0%",
      top: "20%",
      viewControl: {
        alpha: 35, // 视觉角度
        beta: 0, //绘制开始角度
        rotateSensitivity: 2, //扒拉灵敏度
        zoomSensitivity: 2, //缩放灵敏度
        panSensitivity: 1,
        autoRotate: false, //自动旋转开关
        distance: 400, //视觉距离
        autoRotateSpeed: 10, //自动旋转速度
      },
    },
    series: series,
  };
  return option;
}

// export const threeDPieChart = getPie3D(
// [
//   {
//     name: "今年总额",
//     value: 80,
//     itemStyle: {
//       color: "#00a8ff",
//     },
//   },
//   {
//     name: "去年总额",
//     value: 20,
//     itemStyle: {
//       color: "#00d7e9",
//     },
//   },
// ],
// 0
// );


export default getPie3D;
