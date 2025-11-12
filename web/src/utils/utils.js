function generateUUID() {
    var d = new Date().getTime(); //Timestamp
    var d2 = (performance && performance.now && (performance.now()*1000)) || 0; //Time in microseconds since page-load or 0 if unsupported
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16; //random number between 0 and 16
        if(d > 0) { //Use timestamp until depleted
            r = (d + r)%16 | 0;
            d = Math.floor(d/16);
        } else { //Use microseconds since page-load if supported
            r = (d2 + r)%16 | 0;
            d2 = Math.floor(d2/16);
        }
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

function mergeArrays(titleDate, titleName) {
    const nameMap = {};
    titleName.forEach(item => {
      if(item.children){
        item.children.forEach(item => {
          nameMap[item.key] = item.name;
        })
      }
      nameMap[item.key] = item.name;
    });
    // console.log(nameMap)
  
    // 遍历 titleDate 数组，将键转换为对应的 name
    let arr= titleDate.map(item => {
     
      const transformedItem = {};
      for (const key in item) {
        if (item.hasOwnProperty(key)) {
          const name = nameMap[key];
          if (name) {
            transformedItem[name] = item[key];
          }
        }
      }
      return transformedItem;
    });
  
  
    
  
   
    return arr
   
    
  
  }
  function moveKeysToStart(arr, fixedKeys) {
   
    let arr2=[]
    // 固定的键值
    // const fixedKeys = ['费用名称', '费用类型'];
    for(let i =0;i<arr.length;i++){
      arr2.push(sortAndFixKeys(arr[i]))
     
    }
   
    
    function sortAndFixKeys(obj){
      const fixedValues = {};
      const otherValues = {};
  
      // 提取固定的键值
      fixedKeys.forEach(key => {
          if (obj.hasOwnProperty(key)) {
              fixedValues[key] = obj[key];
          }
      });
  
      // 提取其他键值
      Object.keys(obj).forEach(key => {
          if (!fixedKeys.includes(key)) {
              otherValues[key] = obj[key];
          }
      });
  
      // 对其他键值进行排序
      const sortedOtherKeys = Object.keys(otherValues).sort((a, b) => {
          if (a.length === b.length) {
              return a.localeCompare(b, 'zh-CN');
          }
          return a.length - b.length;
      });
  
      // 构建排序后的对象
      const sortedOtherValues = sortedOtherKeys.reduce((acc, key) => {
          acc[key] = otherValues[key];
          return acc;
      }, {});
      
      
  
      // 合并固定的键值和其他排序后的键值
      return { ...fixedValues, ...sortedOtherValues };
      
    }
   return arr2
    
  }
  function moveKeysToEnd(arr, keys) {
    
  return  arr.map(obj=>{
    // 获取所有键
    const allKeys = Object.keys(obj);
    // 分离需要移动到末尾的键和其他键
    const otherKeys = allKeys.filter(key => !keys.includes(key));
    const targetKeys = keys.filter(key => allKeys.includes(key));
    // 重新组合键值对
    const newOrder = [...otherKeys, ...targetKeys];
    // 根据新的顺序创建对象
    return Object.fromEntries(newOrder.map(key => [key, obj[key]]));
  },[])
    
  }
export { generateUUID,
    mergeArrays,
    moveKeysToStart,
    moveKeysToEnd };