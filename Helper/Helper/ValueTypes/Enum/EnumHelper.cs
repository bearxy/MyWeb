using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Reflection;
using System.Text;

namespace Helper
{
    /// <summary>
    /// ö�ٰ�����
    /// </summary>
    public static class EnumHelper
    {
        /// <summary>
        /// ��ö�ٵ��ַ�������ת����ö��
        /// </summary>
        /// <typeparam name="T">ö������</typeparam>
        /// <param name="val">ö��ֵ��Ҳ������ö��int���͵�ֵ</param>
        /// <param name="t">ת���ɹ���ö��ֵ</param>
        /// <returns></returns>
        public static bool IsCanConvert<T>(object val, out T t) where T : struct
        {
            bool result = false;
            t = default(T);
            if (!t.GetType().IsEnum) return false;
            if (Enum.IsDefined(typeof(T), val))
            {
                t = (T)Enum.Parse(typeof(T), val.ToString());
                result = true;
            }
            return result;
        }

        /// <summary>
        /// ��ö��ת��ΪArrayList
        /// ˵����
        /// ������ö�����ͣ��򷵻�NULL
        /// ��Ԫ����-->ͨ��
        /// </summary>
        /// <param name="type">ö������</param>
        /// <returns>ArrayList</returns>
        public static ArrayList ToArrayList(this Type type)
        {
            if (type.IsEnum)
            {
                ArrayList array = new ArrayList();
                Array enumValues = System.Enum.GetValues(type);
                foreach (System.Enum value in enumValues)
                {
                    array.Add(new KeyValuePair<System.Enum, string>(value, GetDescription(value)));
                }
                return array;
            }
            return null;
        }

        /// <summary>
        /// ��ȡö������ֵ��Ӧ����������
        /// </summary>
        /// <typeparam name="TEnum"></typeparam>
        /// <typeparam name="TAttr"></typeparam>
        /// <param name="t"></param>
        /// <param name="value"></param>
        /// <returns></returns>
        public static TAttr GetAttribute<TEnum, TAttr>(TEnum t, object value)
            where TEnum : struct
            where TAttr : Attribute
        {
            if(!t.GetType().IsEnum) return default(TAttr);
            string name = Enum.GetName(typeof(TEnum), value);
            FieldInfo[] fields = t.GetType().GetFields();
            foreach (FieldInfo field in fields)
            {
                if (field.FieldType.IsEnum && field.Name == name)
                {
                    object[] arr = field.GetCustomAttributes(typeof(TAttr), true);
                    if (arr.Length > 0)
                    {
                        return arr[0] as TAttr;
                    }
                }
            }
            return default(TAttr);
        }

        #region ��ȡö�ٵ�DescriptionAttribute ����

        /// <summary>
        /// ��ö���л�ȡDescription
        /// ˵����
        /// ��Ԫ����-->ͨ��
        /// </summary>
        /// <param name="enumName">��Ҫ��ȡö��������ö��</param>
        /// <returns>��������</returns>
        public static string GetDescription(this System.Enum enumName)
        {
            string description;
            FieldInfo fieldInfo = enumName.GetType().GetField(enumName.ToString());
            DescriptionAttribute[] attributes = fieldInfo.GetDescriptAttr();
            if (attributes != null && attributes.Length > 0)
                description = attributes[0].Description;
            else
                description = enumName.ToString();
            return description;
        }
        /// <summary>
        /// ��ȡ�ֶ�Description
        /// </summary>
        /// <param name="fieldInfo">FieldInfo</param>
        /// <returns>DescriptionAttribute[] </returns>
        public static DescriptionAttribute[] GetDescriptAttr(this FieldInfo fieldInfo)
        {
            if (fieldInfo != null)
            {
                return (DescriptionAttribute[])fieldInfo.GetCustomAttributes(typeof(DescriptionAttribute), false);
            }
            return null;
        }
        /// <summary>
        /// ����Description��ȡö��
        /// ˵����
        /// ��Ԫ����-->ͨ��
        /// </summary>
        /// <typeparam name="T">ö������</typeparam>
        /// <param name="description">ö������</param>
        /// <returns>ö��</returns>
        public static T GetEnumName<T>(string description)
        {
            Type _type = typeof(T);
            foreach (FieldInfo field in _type.GetFields())
            {
                DescriptionAttribute[] _curDesc = field.GetDescriptAttr();
                if (_curDesc != null && _curDesc.Length > 0)
                {
                    if (_curDesc[0].Description == description)
                        return (T)field.GetValue(null);
                }
                else
                {
                    if (field.Name == description)
                        return (T)field.GetValue(null);
                }
            }
            throw new ArgumentException(string.Format("{0} δ���ҵ���Ӧ��ö��.", description), "description");
        }

        #endregion 
    }
}
