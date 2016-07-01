using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using EPIOT.DataEntity;

namespace EPIOT.Server
{
    public class DataCheck
    {
        /// <summary>
        /// CRC16校验算法
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public static string CRC16(string data)
        {
            byte[] temp = Encoding.ASCII.GetBytes(data);
            int por = 0xffff;
            for (int j = 0; j < temp.Length; j++)
            {
                por >>= 8;
                por ^= temp[j];

                for (int i = 0; i < 8; i++)
                {
                    if ((por & 0x01) == 1)
                    {
                        por = por >> 1;
                        por = por ^ 0xa001;
                    }
                    else
                        por = por >> 1;
                }
            }
            return string.Format("{0:X4}", por);
        }

        /// <summary>
        /// DTUData,BCC校验算法
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public static string BCC(string data, int _base)
        {
            byte[] temp = Encoding.ASCII.GetBytes(data);
            int por = temp[0];
            for (int i = 1; i < data.Length; i++)
                por ^= temp[i];  //字符转换为ASCII

            if (_base == 10)
                return string.Format("{0:D2}", por);
            else return string.Format("{0:X2}", por);
        }
    }
}
